// Generated from com\github\lopoha\coboltruffle\parser\antlr\CobolPreprocessor.g4 by ANTLR 4.8
package com.github.lopoha.coboltruffle.parser.antlr;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class CobolPreprocessorParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.8", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		COPY=1, SUPPRESS=2, STARTNUMBER=3, COMMENT=4, ENDNUMBER=5, ID=6, NUMBER=7, 
		CHAR=8, INT=9, WS=10, EOL=11, DOT=12, MINUS=13, OPENBRACKET=14, CLOSEBRACKET=15, 
		STRING=16, UNKNOWN=17;
	public static final int
		RULE_file = 0, RULE_copy = 1, RULE_ignored = 2;
	private static String[] makeRuleNames() {
		return new String[] {
			"file", "copy", "ignored"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			"'.'", "'-'", "'('", "')'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "COPY", "SUPPRESS", "STARTNUMBER", "COMMENT", "ENDNUMBER", "ID", 
			"NUMBER", "CHAR", "INT", "WS", "EOL", "DOT", "MINUS", "OPENBRACKET", 
			"CLOSEBRACKET", "STRING", "UNKNOWN"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "CobolPreprocessor.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public CobolPreprocessorParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class FileContext extends ParserRuleContext {
		public List<CopyContext> copy() {
			return getRuleContexts(CopyContext.class);
		}
		public CopyContext copy(int i) {
			return getRuleContext(CopyContext.class,i);
		}
		public List<IgnoredContext> ignored() {
			return getRuleContexts(IgnoredContext.class);
		}
		public IgnoredContext ignored(int i) {
			return getRuleContext(IgnoredContext.class,i);
		}
		public FileContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_file; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CobolPreprocessorListener ) ((CobolPreprocessorListener)listener).enterFile(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CobolPreprocessorListener ) ((CobolPreprocessorListener)listener).exitFile(this);
		}
	}

	public final FileContext file() throws RecognitionException {
		FileContext _localctx = new FileContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_file);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(10);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << COPY) | (1L << SUPPRESS) | (1L << STARTNUMBER) | (1L << COMMENT) | (1L << ENDNUMBER) | (1L << ID) | (1L << NUMBER) | (1L << CHAR) | (1L << INT) | (1L << WS) | (1L << EOL) | (1L << DOT) | (1L << MINUS) | (1L << OPENBRACKET) | (1L << CLOSEBRACKET) | (1L << STRING) | (1L << UNKNOWN))) != 0)) {
				{
				setState(8);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
				case 1:
					{
					setState(6);
					copy();
					}
					break;
				case 2:
					{
					setState(7);
					ignored();
					}
					break;
				}
				}
				setState(12);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CopyContext extends ParserRuleContext {
		public TerminalNode COPY() { return getToken(CobolPreprocessorParser.COPY, 0); }
		public TerminalNode ID() { return getToken(CobolPreprocessorParser.ID, 0); }
		public TerminalNode DOT() { return getToken(CobolPreprocessorParser.DOT, 0); }
		public TerminalNode SUPPRESS() { return getToken(CobolPreprocessorParser.SUPPRESS, 0); }
		public CopyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_copy; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CobolPreprocessorListener ) ((CobolPreprocessorListener)listener).enterCopy(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CobolPreprocessorListener ) ((CobolPreprocessorListener)listener).exitCopy(this);
		}
	}

	public final CopyContext copy() throws RecognitionException {
		CopyContext _localctx = new CopyContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_copy);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(13);
			match(COPY);
			setState(14);
			match(ID);
			setState(16);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SUPPRESS) {
				{
				setState(15);
				match(SUPPRESS);
				}
			}

			setState(18);
			match(DOT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IgnoredContext extends ParserRuleContext {
		public IgnoredContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ignored; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CobolPreprocessorListener ) ((CobolPreprocessorListener)listener).enterIgnored(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CobolPreprocessorListener ) ((CobolPreprocessorListener)listener).exitIgnored(this);
		}
	}

	public final IgnoredContext ignored() throws RecognitionException {
		IgnoredContext _localctx = new IgnoredContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_ignored);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(20);
			matchWildcard();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\23\31\4\2\t\2\4\3"+
		"\t\3\4\4\t\4\3\2\3\2\7\2\13\n\2\f\2\16\2\16\13\2\3\3\3\3\3\3\5\3\23\n"+
		"\3\3\3\3\3\3\4\3\4\3\4\2\2\5\2\4\6\2\2\2\30\2\f\3\2\2\2\4\17\3\2\2\2\6"+
		"\26\3\2\2\2\b\13\5\4\3\2\t\13\5\6\4\2\n\b\3\2\2\2\n\t\3\2\2\2\13\16\3"+
		"\2\2\2\f\n\3\2\2\2\f\r\3\2\2\2\r\3\3\2\2\2\16\f\3\2\2\2\17\20\7\3\2\2"+
		"\20\22\7\b\2\2\21\23\7\4\2\2\22\21\3\2\2\2\22\23\3\2\2\2\23\24\3\2\2\2"+
		"\24\25\7\16\2\2\25\5\3\2\2\2\26\27\13\2\2\2\27\7\3\2\2\2\5\n\f\22";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}