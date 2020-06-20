// Generated from com\github\lopoha\coboltruffle\parser\antlr\CobolPreprocessor.g4 by ANTLR 4.8
package com.github.lopoha.coboltruffle.parser.antlr;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class CobolPreprocessorLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.8", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		COPY=1, SUPPRESS=2, STARTNUMBER=3, COMMENT=4, ENDNUMBER=5, ID=6, NUMBER=7, 
		CHAR=8, INT=9, WS=10, EOL=11, DOT=12, MINUS=13, OPENBRACKET=14, CLOSEBRACKET=15, 
		STRING=16, UNKNOWN=17;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"COPY", "SUPPRESS", "STARTNUMBER", "COMMENT", "ENDNUMBER", "ID", "NUMBER", 
			"CHAR", "INT", "WS", "EOL", "DOT", "MINUS", "OPENBRACKET", "CLOSEBRACKET", 
			"STRING", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", 
			"M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", 
			"UNKNOWN"
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


	public CobolPreprocessorLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "CobolPreprocessor.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	@Override
	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 2:
			return STARTNUMBER_sempred((RuleContext)_localctx, predIndex);
		case 3:
			return COMMENT_sempred((RuleContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean STARTNUMBER_sempred(RuleContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return getCharPositionInLine() == 1;
		}
		return true;
	}
	private boolean COMMENT_sempred(RuleContext _localctx, int predIndex) {
		switch (predIndex) {
		case 1:
			return getCharPositionInLine() == 7;
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\23\u00e7\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t"+
		" \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t"+
		"+\4,\t,\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\4\3"+
		"\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\7\5u\n\5\f\5\16\5x\13\5"+
		"\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\5\6\u0086\n\6\3\6\3\6"+
		"\3\7\3\7\3\7\3\7\6\7\u008e\n\7\r\7\16\7\u008f\3\b\6\b\u0093\n\b\r\b\16"+
		"\b\u0094\3\t\3\t\3\n\3\n\3\13\3\13\3\13\3\13\3\f\3\f\3\r\3\r\3\16\3\16"+
		"\3\17\3\17\3\20\3\20\3\21\3\21\7\21\u00ab\n\21\f\21\16\21\u00ae\13\21"+
		"\3\21\3\21\3\22\3\22\3\23\3\23\3\24\3\24\3\25\3\25\3\26\3\26\3\27\3\27"+
		"\3\30\3\30\3\31\3\31\3\32\3\32\3\33\3\33\3\34\3\34\3\35\3\35\3\36\3\36"+
		"\3\37\3\37\3 \3 \3!\3!\3\"\3\"\3#\3#\3$\3$\3%\3%\3&\3&\3\'\3\'\3(\3(\3"+
		")\3)\3*\3*\3+\3+\3,\3,\3\u00ac\2-\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23"+
		"\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\2%\2\'\2)\2+\2-\2/\2\61\2\63"+
		"\2\65\2\67\29\2;\2=\2?\2A\2C\2E\2G\2I\2K\2M\2O\2Q\2S\2U\2W\23\3\2!\4\2"+
		",,\61\61\4\2\f\f\17\17\4\2C\\c|\3\2\62;\5\2\13\f\17\17\"\"\4\2CCcc\4\2"+
		"DDdd\4\2EEee\4\2FFff\4\2GGgg\4\2HHhh\4\2IIii\4\2JJjj\4\2KKkk\4\2LLll\4"+
		"\2MMmm\4\2NNnn\4\2OOoo\4\2PPpp\4\2QQqq\4\2RRrr\4\2SSss\4\2TTtt\4\2UUu"+
		"u\4\2VVvv\4\2WWww\4\2XXxx\4\2YYyy\4\2ZZzz\4\2[[{{\4\2\\\\||\2\u00d3\2"+
		"\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2"+
		"\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2"+
		"\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2W\3\2\2"+
		"\2\3Y\3\2\2\2\5^\3\2\2\2\7g\3\2\2\2\tq\3\2\2\2\13{\3\2\2\2\r\u0089\3\2"+
		"\2\2\17\u0092\3\2\2\2\21\u0096\3\2\2\2\23\u0098\3\2\2\2\25\u009a\3\2\2"+
		"\2\27\u009e\3\2\2\2\31\u00a0\3\2\2\2\33\u00a2\3\2\2\2\35\u00a4\3\2\2\2"+
		"\37\u00a6\3\2\2\2!\u00a8\3\2\2\2#\u00b1\3\2\2\2%\u00b3\3\2\2\2\'\u00b5"+
		"\3\2\2\2)\u00b7\3\2\2\2+\u00b9\3\2\2\2-\u00bb\3\2\2\2/\u00bd\3\2\2\2\61"+
		"\u00bf\3\2\2\2\63\u00c1\3\2\2\2\65\u00c3\3\2\2\2\67\u00c5\3\2\2\29\u00c7"+
		"\3\2\2\2;\u00c9\3\2\2\2=\u00cb\3\2\2\2?\u00cd\3\2\2\2A\u00cf\3\2\2\2C"+
		"\u00d1\3\2\2\2E\u00d3\3\2\2\2G\u00d5\3\2\2\2I\u00d7\3\2\2\2K\u00d9\3\2"+
		"\2\2M\u00db\3\2\2\2O\u00dd\3\2\2\2Q\u00df\3\2\2\2S\u00e1\3\2\2\2U\u00e3"+
		"\3\2\2\2W\u00e5\3\2\2\2YZ\5\'\24\2Z[\5? \2[\\\5A!\2\\]\5S*\2]\4\3\2\2"+
		"\2^_\5G$\2_`\5K&\2`a\5A!\2ab\5A!\2bc\5E#\2cd\5+\26\2de\5G$\2ef\5G$\2f"+
		"\6\3\2\2\2gh\5\23\n\2hi\6\4\2\2ij\5\23\n\2jk\5\23\n\2kl\5\23\n\2lm\5\23"+
		"\n\2mn\5\23\n\2no\3\2\2\2op\b\4\2\2p\b\3\2\2\2qr\t\2\2\2rv\6\5\3\2su\n"+
		"\3\2\2ts\3\2\2\2ux\3\2\2\2vt\3\2\2\2vw\3\2\2\2wy\3\2\2\2xv\3\2\2\2yz\b"+
		"\5\2\2z\n\3\2\2\2{|\5\23\n\2|}\5\23\n\2}~\5\23\n\2~\177\5\23\n\2\177\u0080"+
		"\5\23\n\2\u0080\u0081\5\23\n\2\u0081\u0082\5\23\n\2\u0082\u0085\5\23\n"+
		"\2\u0083\u0086\5\27\f\2\u0084\u0086\7\2\2\3\u0085\u0083\3\2\2\2\u0085"+
		"\u0084\3\2\2\2\u0086\u0087\3\2\2\2\u0087\u0088\b\6\2\2\u0088\f\3\2\2\2"+
		"\u0089\u008d\5\21\t\2\u008a\u008e\5\21\t\2\u008b\u008e\5\23\n\2\u008c"+
		"\u008e\5\33\16\2\u008d\u008a\3\2\2\2\u008d\u008b\3\2\2\2\u008d\u008c\3"+
		"\2\2\2\u008e\u008f\3\2\2\2\u008f\u008d\3\2\2\2\u008f\u0090\3\2\2\2\u0090"+
		"\16\3\2\2\2\u0091\u0093\5\23\n\2\u0092\u0091\3\2\2\2\u0093\u0094\3\2\2"+
		"\2\u0094\u0092\3\2\2\2\u0094\u0095\3\2\2\2\u0095\20\3\2\2\2\u0096\u0097"+
		"\t\4\2\2\u0097\22\3\2\2\2\u0098\u0099\t\5\2\2\u0099\24\3\2\2\2\u009a\u009b"+
		"\t\6\2\2\u009b\u009c\3\2\2\2\u009c\u009d\b\13\2\2\u009d\26\3\2\2\2\u009e"+
		"\u009f\t\3\2\2\u009f\30\3\2\2\2\u00a0\u00a1\7\60\2\2\u00a1\32\3\2\2\2"+
		"\u00a2\u00a3\7/\2\2\u00a3\34\3\2\2\2\u00a4\u00a5\7*\2\2\u00a5\36\3\2\2"+
		"\2\u00a6\u00a7\7+\2\2\u00a7 \3\2\2\2\u00a8\u00ac\7$\2\2\u00a9\u00ab\13"+
		"\2\2\2\u00aa\u00a9\3\2\2\2\u00ab\u00ae\3\2\2\2\u00ac\u00ad\3\2\2\2\u00ac"+
		"\u00aa\3\2\2\2\u00ad\u00af\3\2\2\2\u00ae\u00ac\3\2\2\2\u00af\u00b0\7$"+
		"\2\2\u00b0\"\3\2\2\2\u00b1\u00b2\t\7\2\2\u00b2$\3\2\2\2\u00b3\u00b4\t"+
		"\b\2\2\u00b4&\3\2\2\2\u00b5\u00b6\t\t\2\2\u00b6(\3\2\2\2\u00b7\u00b8\t"+
		"\n\2\2\u00b8*\3\2\2\2\u00b9\u00ba\t\13\2\2\u00ba,\3\2\2\2\u00bb\u00bc"+
		"\t\f\2\2\u00bc.\3\2\2\2\u00bd\u00be\t\r\2\2\u00be\60\3\2\2\2\u00bf\u00c0"+
		"\t\16\2\2\u00c0\62\3\2\2\2\u00c1\u00c2\t\17\2\2\u00c2\64\3\2\2\2\u00c3"+
		"\u00c4\t\20\2\2\u00c4\66\3\2\2\2\u00c5\u00c6\t\21\2\2\u00c68\3\2\2\2\u00c7"+
		"\u00c8\t\22\2\2\u00c8:\3\2\2\2\u00c9\u00ca\t\23\2\2\u00ca<\3\2\2\2\u00cb"+
		"\u00cc\t\24\2\2\u00cc>\3\2\2\2\u00cd\u00ce\t\25\2\2\u00ce@\3\2\2\2\u00cf"+
		"\u00d0\t\26\2\2\u00d0B\3\2\2\2\u00d1\u00d2\t\27\2\2\u00d2D\3\2\2\2\u00d3"+
		"\u00d4\t\30\2\2\u00d4F\3\2\2\2\u00d5\u00d6\t\31\2\2\u00d6H\3\2\2\2\u00d7"+
		"\u00d8\t\32\2\2\u00d8J\3\2\2\2\u00d9\u00da\t\33\2\2\u00daL\3\2\2\2\u00db"+
		"\u00dc\t\34\2\2\u00dcN\3\2\2\2\u00dd\u00de\t\35\2\2\u00deP\3\2\2\2\u00df"+
		"\u00e0\t\36\2\2\u00e0R\3\2\2\2\u00e1\u00e2\t\37\2\2\u00e2T\3\2\2\2\u00e3"+
		"\u00e4\t \2\2\u00e4V\3\2\2\2\u00e5\u00e6\13\2\2\2\u00e6X\3\2\2\2\t\2v"+
		"\u0085\u008d\u008f\u0094\u00ac\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}