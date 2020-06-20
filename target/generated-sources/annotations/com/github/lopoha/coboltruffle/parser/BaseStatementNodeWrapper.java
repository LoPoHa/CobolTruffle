// CheckStyle: start generated

package com.github.lopoha.coboltruffle.parser;


import com.github.lopoha.coboltruffle.parser.BaseStatementNode;

import com.oracle.truffle.api.dsl.GeneratedBy;

import com.oracle.truffle.api.frame.VirtualFrame;

import com.oracle.truffle.api.instrumentation.ProbeNode;

import com.oracle.truffle.api.instrumentation.InstrumentableNode.WrapperNode;

import com.oracle.truffle.api.nodes.NodeCost;


@GeneratedBy(BaseStatementNode.class)

final class BaseStatementNodeWrapper extends BaseStatementNode implements WrapperNode {


    @Child private BaseStatementNode delegateNode;

    @Child private ProbeNode probeNode;


    BaseStatementNodeWrapper(BaseStatementNode delegateNode, ProbeNode probeNode) {

        this.delegateNode = delegateNode;

        this.probeNode = probeNode;

    }


    @Override

    public BaseStatementNode getDelegateNode() {

        return delegateNode;

    }


    @Override

    public ProbeNode getProbeNode() {

        return probeNode;

    }


    @Override

    public NodeCost getCost() {

        return NodeCost.NONE;

    }


    @Override

    public void executeVoid(VirtualFrame frame) {

        for (;;) {

            boolean wasOnReturnExecuted = false;

            try {

                probeNode.onEnter(frame);

                delegateNode.executeVoid(frame);

                wasOnReturnExecuted = true;

                probeNode.onReturnValue(frame, null);

                break;

            } catch (Throwable t) {

                Object result = probeNode.onReturnExceptionalOrUnwind(frame, t, wasOnReturnExecuted);

                if (result == ProbeNode.UNWIND_ACTION_REENTER) {

                    continue;

                } else if (result != null) {

                    break;

                }

                throw t;

            }

        }

    }


}
