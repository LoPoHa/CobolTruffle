package com.github.lopoha.coboltruffle.nodes.local;

// modeled / copied from CobolLexicalScope

import com.github.lopoha.coboltruffle.nodes.CobolEvalRootNode;
import com.github.lopoha.coboltruffle.nodes.CobolRootNode;
import com.github.lopoha.coboltruffle.nodes.controlflow.CobolBlockNode;
import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RootNode;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/** Simple language lexical scope. There can be a block scope, or function scope. */
public final class CobolLexicalScope {

  private final Node current;
  private final CobolBlockNode block;
  private final CobolBlockNode parentBlock;
  private final RootNode root;
  private CobolLexicalScope parent;
  private Map<String, FrameSlot> varSlots;

  private CobolLexicalScope(Node current, CobolBlockNode block, CobolBlockNode parentBlock) {
    this.current = current;
    this.block = block;
    this.parentBlock = parentBlock;
    this.root = null;
  }

  private CobolLexicalScope(Node current, CobolBlockNode block, RootNode root) {
    this.current = current;
    this.block = block;
    this.parentBlock = null;
    this.root = root;
  }

  /**
   * todo.
   *
   * @param node todo.
   * @return todo
   */
  // The parameter node should not be assigned
  public static CobolLexicalScope createScope(Node node) {
    CobolBlockNode block = getParentBlock(node);
    if (block == null) {
      // We're in the root.
      block = findChildrenBlock(node);
      if (block == null) {
        RootNode root = node.getRootNode();
        assert root instanceof CobolEvalRootNode || root instanceof CobolRootNode
            : "Corrupted Cobol AST under " + node;
        return new CobolLexicalScope(null, null, (CobolBlockNode) null);
      }
      node = null;
    }

    CobolBlockNode parentBlock = getParentBlock(block);
    if (parentBlock == null) {
      return new CobolLexicalScope(node, block, block.getRootNode());
    } else {
      return new CobolLexicalScope(node, block, parentBlock);
    }
  }

  private static CobolBlockNode getParentBlock(Node node) {
    CobolBlockNode block;
    Node parent = node.getParent();
    // Find a nearest block node.
    while (parent != null && !(parent instanceof CobolBlockNode)) {
      parent = parent.getParent();
    }
    if (parent != null) {
      block = (CobolBlockNode) parent;
    } else {
      block = null;
    }
    return block;
  }

  private static CobolBlockNode findChildrenBlock(Node node) {
    CobolBlockNode[] blockPtr = new CobolBlockNode[1];
    node.accept(
        n -> {
          if (n instanceof CobolBlockNode) {
            blockPtr[0] = (CobolBlockNode) n;
            return false;
          } else {
            return true;
          }
        });
    return blockPtr[0];
  }

  private static Map<String, FrameSlot> collectArgs(Node block) {
    // Arguments are pushed to frame slots at the beginning of the function block.
    // To collect argument slots, search for SLReadArgumentNode inside of
    // SLWriteLocalVariableNode.
    /*
    NodeUtil.forEachChild(block, new NodeVisitor() {

        private SLWriteLocalVariableNode wn; // The current write node containing a slot

        @Override
        public boolean visit(Node node) {
            // When there is a write node, search for SLReadArgumentNode among its children:
            if (node instanceof SLWriteLocalVariableNode) {
                wn = (SLWriteLocalVariableNode) node;
                boolean all = NodeUtil.forEachChild(node, this);
                wn = null;
                return all;
            } else if (wn != null && (node instanceof SLReadArgumentNode)) {
                FrameSlot slot = wn.getSlot();
                String name = Objects.toString(slot.getIdentifier());
                assert !args.containsKey(name) : name + " argument exists already.";
                args.put(name, slot);
                return true;
            } else if (wn == null && (node instanceof SLStatementNode)) {
                // A different SL node - we're done.
                return false;
            } else {
                return NodeUtil.forEachChild(node, this);
            }
        }
    });
    */
    return new LinkedHashMap<>(4);
  }

  /**
   * Todo.
   *
   * @return todo.
   */
  public CobolLexicalScope findParent() {
    if (parentBlock == null) {
      // This was a root scope.
      return null;
    }
    if (parent == null) {
      Node node = block;
      CobolBlockNode newBlock = parentBlock;
      // Test if there is a next parent block. If not, we're in the root scope.
      CobolBlockNode newParentBlock = getParentBlock(newBlock);
      if (newParentBlock == null) {
        parent = new CobolLexicalScope(node, newBlock, newBlock.getRootNode());
      } else {
        parent = new CobolLexicalScope(node, newBlock, newParentBlock);
      }
    }
    return parent;
  }

  /**
   * todo.
   *
   * @return the function name for function scope, "block" otherwise.
   */
  public String getName() {
    if (root != null) {
      return root.getName();
    } else {
      return "block";
    }
  }

  /**
   * todo.
   *
   * @return the node representing the scope, the block node for block scopes and the {@link
   *     RootNode} for functional scope.
   */
  public Node getNode() {
    if (root != null) {
      return root;
    } else {
      return block;
    }
  }

  /**
   * todo.
   *
   * @param frame todo
   * @return todo
   */
  public Object getVariables(Frame frame) {
    Map<String, FrameSlot> vars = getVars();
    Object[] args = null;
    // Use arguments when the current node is above the block
    if (current == null) {
      args = (frame != null) ? frame.getArguments() : null;
    }
    return new VariablesMapObject(vars, args, frame);
  }

  /**
   * todo.
   *
   * @param frame todo
   * @return todo
   */
  public Object getArguments(Frame frame) {
    if (root == null) {
      // No arguments for block scope
      return null;
    }
    // The slots give us names of the arguments:
    Map<String, FrameSlot> argSlots = collectArgs(block);
    // The frame's arguments array give us the argument values:
    Object[] args = (frame != null) ? frame.getArguments() : null;
    // Create a TruffleObject having the arguments as properties:
    return new VariablesMapObject(argSlots, args, frame);
  }

  private Map<String, FrameSlot> getVars() {
    if (varSlots == null) {
      if (current != null) {
        varSlots = collectVars(block, current);
      } else if (block != null) {
        // Provide the arguments only when the current node is above the block
        varSlots = collectArgs(block);
      } else {
        varSlots = Collections.emptyMap();
      }
    }
    return varSlots;
  }

  private boolean hasParentVar(String name) {
    CobolLexicalScope p = this;
    while ((p = p.findParent()) != null) {
      if (p.getVars().containsKey(name)) {
        return true;
      }
    }
    return false;
  }

  private Map<String, FrameSlot> collectVars(Node varsBlock, Node currentNode) {
    /*
    NodeUtil.forEachChild(varsBlock, new NodeVisitor() {
        @Override
        public boolean visit(Node node) {
            if (node == currentNode) {
                return false;
            }
            // Do not enter any nested blocks.
            if (!(node instanceof CobolBlockNode)) {
                boolean all = NodeUtil.forEachChild(node, this);
                if (!all) {
                    return false;
                }
            }
            // Write to a variable is a declaration unless it exists already in a parent scope.
            if (node instanceof CobolWriteLocalVariableNode) {
                SLWriteLocalVariableNode wn = (SLWriteLocalVariableNode) node;
                String name = Objects.toString(wn.getSlot().getIdentifier());
                if (!hasParentVar(name)) {
                    slots.put(name, wn.getSlot());
                }
            }
            return true;
        }
    });
    */
    return new LinkedHashMap<>(4);
  }

  @ExportLibrary(InteropLibrary.class)
  static final class VariablesMapObject implements TruffleObject {

    final Map<String, ? extends FrameSlot> slots;
    final Object[] args;
    final Frame frame;

    private VariablesMapObject(Map<String, ? extends FrameSlot> slots, Object[] args, Frame frame) {
      this.slots = slots;
      this.args = args;
      this.frame = frame;
    }

    @SuppressWarnings("static-method")
    @ExportMessage
    boolean hasMembers() {
      return true;
    }

    @ExportMessage
    @TruffleBoundary
    Object getMembers(@SuppressWarnings("unused") boolean includeInternal) {
      return new KeysArray(slots.keySet().toArray(new String[0]));
    }

    @ExportMessage
    @TruffleBoundary
    void writeMember(String member, Object value)
        throws UnsupportedMessageException, UnknownIdentifierException {
      if (frame == null) {
        throw UnsupportedMessageException.create();
      }
      FrameSlot slot = slots.get(member);
      if (slot == null) {
        throw UnknownIdentifierException.create(member);
      } else {
        Object info = slot.getInfo();
        if (args != null && info != null) {
          args[(Integer) info] = value;
        } else {
          frame.setObject(slot, value);
        }
      }
    }

    @ExportMessage
    @TruffleBoundary
    Object readMember(String member) throws UnknownIdentifierException {
      throw UnknownIdentifierException.create(member);
      /*
      if (frame == null) {
          return SLNull.SINGLETON;
      }
      FrameSlot slot = slots.get(member);
      if (slot == null) {
          throw UnknownIdentifierException.create(member);
      } else {
          Object value;
          Object info = slot.getInfo();
          if (args != null && info != null) {
              value = args[(Integer) info];
          } else {
              value = frame.getValue(slot);
          }
          return value;
      }
      */
    }

    @SuppressWarnings("static-method")
    @ExportMessage
    boolean isMemberInsertable(@SuppressWarnings("unused") String member) {
      return false;
    }

    @ExportMessage
    @TruffleBoundary
    boolean isMemberModifiable(String member) {
      return slots.containsKey(member);
    }

    @ExportMessage
    @TruffleBoundary
    boolean isMemberReadable(String member) {
      return frame == null || slots.containsKey(member);
    }
  }

  @ExportLibrary(InteropLibrary.class)
  static final class KeysArray implements TruffleObject {

    private final String[] keys;

    KeysArray(String[] keys) {
      this.keys = keys;
    }

    @SuppressWarnings("static-method")
    @ExportMessage
    boolean hasArrayElements() {
      return true;
    }

    @ExportMessage
    boolean isArrayElementReadable(long index) {
      return index >= 0 && index < keys.length;
    }

    @ExportMessage
    long getArraySize() {
      return keys.length;
    }

    @ExportMessage
    Object readArrayElement(long index) throws InvalidArrayIndexException {
      if (!isArrayElementReadable(index)) {
        throw InvalidArrayIndexException.create(index);
      }
      return keys[(int) index];
    }
  }
}
