// Generated from c:\Users\chdereck\Desktop\cobol_truffle\parser\antlr\CommonLexerRules.g4 by ANTLR 4.7.1
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class CommonLexerRules extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		ID=1, NUMBER=2, CHAR=3, INT=4, WS=5, EOL=6, DOT=7, MINUS=8, OPENBRACKET=9, 
		CLOSEBRACKET=10, STRING=11, UNKNOWN=12;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"ID", "NUMBER", "CHAR", "INT", "WS", "EOL", "DOT", "MINUS", "OPENBRACKET", 
		"CLOSEBRACKET", "STRING", "A", "B", "C", "D", "E", "F", "G", "H", "I", 
		"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", 
		"X", "Y", "Z", "UNKNOWN"
	};

	private static final String[] _LITERAL_NAMES = {
		null, null, null, null, null, null, null, "'.'", "'-'", "'('", "')'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "ID", "NUMBER", "CHAR", "INT", "WS", "EOL", "DOT", "MINUS", "OPENBRACKET", 
		"CLOSEBRACKET", "STRING", "UNKNOWN"
	};
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


	public CommonLexerRules(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "CommonLexerRules.g4"; }

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

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\16\u00ad\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t"+
		" \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\3\2\3\2\3\2\3\2\6\2T\n"+
		"\2\r\2\16\2U\3\3\6\3Y\n\3\r\3\16\3Z\3\4\3\4\3\5\3\5\3\6\3\6\3\6\3\6\3"+
		"\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\3\f\3\f\7\fq\n\f\f\f\16\ft\13"+
		"\f\3\f\3\f\3\r\3\r\3\16\3\16\3\17\3\17\3\20\3\20\3\21\3\21\3\22\3\22\3"+
		"\23\3\23\3\24\3\24\3\25\3\25\3\26\3\26\3\27\3\27\3\30\3\30\3\31\3\31\3"+
		"\32\3\32\3\33\3\33\3\34\3\34\3\35\3\35\3\36\3\36\3\37\3\37\3 \3 \3!\3"+
		"!\3\"\3\"\3#\3#\3$\3$\3%\3%\3&\3&\3\'\3\'\3r\2(\3\3\5\4\7\5\t\6\13\7\r"+
		"\b\17\t\21\n\23\13\25\f\27\r\31\2\33\2\35\2\37\2!\2#\2%\2\'\2)\2+\2-\2"+
		"/\2\61\2\63\2\65\2\67\29\2;\2=\2?\2A\2C\2E\2G\2I\2K\2M\16\3\2 \4\2C\\"+
		"c|\3\2\62;\5\2\13\f\17\17\"\"\4\2\f\f\17\17\4\2CCcc\4\2DDdd\4\2EEee\4"+
		"\2FFff\4\2GGgg\4\2HHhh\4\2IIii\4\2JJjj\4\2KKkk\4\2LLll\4\2MMmm\4\2NNn"+
		"n\4\2OOoo\4\2PPpp\4\2QQqq\4\2RRrr\4\2SSss\4\2TTtt\4\2UUuu\4\2VVvv\4\2"+
		"WWww\4\2XXxx\4\2YYyy\4\2ZZzz\4\2[[{{\4\2\\\\||\2\u0097\2\3\3\2\2\2\2\5"+
		"\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2"+
		"\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2M\3\2\2\2\3O\3\2"+
		"\2\2\5X\3\2\2\2\7\\\3\2\2\2\t^\3\2\2\2\13`\3\2\2\2\rd\3\2\2\2\17f\3\2"+
		"\2\2\21h\3\2\2\2\23j\3\2\2\2\25l\3\2\2\2\27n\3\2\2\2\31w\3\2\2\2\33y\3"+
		"\2\2\2\35{\3\2\2\2\37}\3\2\2\2!\177\3\2\2\2#\u0081\3\2\2\2%\u0083\3\2"+
		"\2\2\'\u0085\3\2\2\2)\u0087\3\2\2\2+\u0089\3\2\2\2-\u008b\3\2\2\2/\u008d"+
		"\3\2\2\2\61\u008f\3\2\2\2\63\u0091\3\2\2\2\65\u0093\3\2\2\2\67\u0095\3"+
		"\2\2\29\u0097\3\2\2\2;\u0099\3\2\2\2=\u009b\3\2\2\2?\u009d\3\2\2\2A\u009f"+
		"\3\2\2\2C\u00a1\3\2\2\2E\u00a3\3\2\2\2G\u00a5\3\2\2\2I\u00a7\3\2\2\2K"+
		"\u00a9\3\2\2\2M\u00ab\3\2\2\2OS\5\7\4\2PT\5\7\4\2QT\5\t\5\2RT\5\21\t\2"+
		"SP\3\2\2\2SQ\3\2\2\2SR\3\2\2\2TU\3\2\2\2US\3\2\2\2UV\3\2\2\2V\4\3\2\2"+
		"\2WY\5\t\5\2XW\3\2\2\2YZ\3\2\2\2ZX\3\2\2\2Z[\3\2\2\2[\6\3\2\2\2\\]\t\2"+
		"\2\2]\b\3\2\2\2^_\t\3\2\2_\n\3\2\2\2`a\t\4\2\2ab\3\2\2\2bc\b\6\2\2c\f"+
		"\3\2\2\2de\t\5\2\2e\16\3\2\2\2fg\7\60\2\2g\20\3\2\2\2hi\7/\2\2i\22\3\2"+
		"\2\2jk\7*\2\2k\24\3\2\2\2lm\7+\2\2m\26\3\2\2\2nr\7$\2\2oq\13\2\2\2po\3"+
		"\2\2\2qt\3\2\2\2rs\3\2\2\2rp\3\2\2\2su\3\2\2\2tr\3\2\2\2uv\7$\2\2v\30"+
		"\3\2\2\2wx\t\6\2\2x\32\3\2\2\2yz\t\7\2\2z\34\3\2\2\2{|\t\b\2\2|\36\3\2"+
		"\2\2}~\t\t\2\2~ \3\2\2\2\177\u0080\t\n\2\2\u0080\"\3\2\2\2\u0081\u0082"+
		"\t\13\2\2\u0082$\3\2\2\2\u0083\u0084\t\f\2\2\u0084&\3\2\2\2\u0085\u0086"+
		"\t\r\2\2\u0086(\3\2\2\2\u0087\u0088\t\16\2\2\u0088*\3\2\2\2\u0089\u008a"+
		"\t\17\2\2\u008a,\3\2\2\2\u008b\u008c\t\20\2\2\u008c.\3\2\2\2\u008d\u008e"+
		"\t\21\2\2\u008e\60\3\2\2\2\u008f\u0090\t\22\2\2\u0090\62\3\2\2\2\u0091"+
		"\u0092\t\23\2\2\u0092\64\3\2\2\2\u0093\u0094\t\24\2\2\u0094\66\3\2\2\2"+
		"\u0095\u0096\t\25\2\2\u00968\3\2\2\2\u0097\u0098\t\26\2\2\u0098:\3\2\2"+
		"\2\u0099\u009a\t\27\2\2\u009a<\3\2\2\2\u009b\u009c\t\30\2\2\u009c>\3\2"+
		"\2\2\u009d\u009e\t\31\2\2\u009e@\3\2\2\2\u009f\u00a0\t\32\2\2\u00a0B\3"+
		"\2\2\2\u00a1\u00a2\t\33\2\2\u00a2D\3\2\2\2\u00a3\u00a4\t\34\2\2\u00a4"+
		"F\3\2\2\2\u00a5\u00a6\t\35\2\2\u00a6H\3\2\2\2\u00a7\u00a8\t\36\2\2\u00a8"+
		"J\3\2\2\2\u00a9\u00aa\t\37\2\2\u00aaL\3\2\2\2\u00ab\u00ac\13\2\2\2\u00ac"+
		"N\3\2\2\2\7\2SUZr\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}