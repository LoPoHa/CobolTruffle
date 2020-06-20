// Generated from com\github\lopoha\coboltruffle\parser\antlr\Cobol.g4 by ANTLR 4.8
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
public class CobolLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.8", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		IDENTIFICATION=1, ENVIRONMENT=2, CONFIGURATION=3, SPECIALNAMES=4, DECIMALPOINT=5, 
		INPUTOUTPUT=6, FILECONTROL=7, DATA=8, FILE=9, WORKINGSTORAGE=10, LINKAGE=11, 
		PROCEDURE=12, USING=13, DIVISION=14, SECTION=15, TRUE=16, FALSE=17, INITIALIZE=18, 
		EQUAL=19, LESS=20, NUMERIC=21, END=22, EXIT=23, PROGRAM=24, PERFORM=25, 
		MOVE=26, TO=27, IF=28, THEN=29, ELSE=30, ENDIF=31, IS=32, PIC=33, COMMA=34, 
		PICXS=35, PICX=36, PIC9S=37, PIC9=38, VALUE=39, SPACE=40, PROGRAMID=41, 
		REDEFINES=42, FILLER=43, ID=44, NUMBER=45, CHAR=46, INT=47, WS=48, EOL=49, 
		DOT=50, MINUS=51, OPENBRACKET=52, CLOSEBRACKET=53, STRING=54, UNKNOWN=55;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"IDENTIFICATION", "ENVIRONMENT", "CONFIGURATION", "SPECIALNAMES", "DECIMALPOINT", 
			"INPUTOUTPUT", "FILECONTROL", "DATA", "FILE", "WORKINGSTORAGE", "LINKAGE", 
			"PROCEDURE", "USING", "DIVISION", "SECTION", "TRUE", "FALSE", "INITIALIZE", 
			"EQUAL", "LESS", "NUMERIC", "END", "EXIT", "PROGRAM", "PERFORM", "MOVE", 
			"TO", "IF", "THEN", "ELSE", "ENDIF", "IS", "PIC", "COMMA", "PICXS", "PICX", 
			"PIC9S", "PIC9", "VALUE", "SPACE", "PROGRAMID", "REDEFINES", "FILLER", 
			"ID", "NUMBER", "CHAR", "INT", "WS", "EOL", "DOT", "MINUS", "OPENBRACKET", 
			"CLOSEBRACKET", "STRING", "A", "B", "C", "D", "E", "F", "G", "H", "I", 
			"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", 
			"X", "Y", "Z", "UNKNOWN"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, "'9'", null, null, null, null, null, null, null, null, null, 
			null, null, "'.'", "'-'", "'('", "')'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "IDENTIFICATION", "ENVIRONMENT", "CONFIGURATION", "SPECIALNAMES", 
			"DECIMALPOINT", "INPUTOUTPUT", "FILECONTROL", "DATA", "FILE", "WORKINGSTORAGE", 
			"LINKAGE", "PROCEDURE", "USING", "DIVISION", "SECTION", "TRUE", "FALSE", 
			"INITIALIZE", "EQUAL", "LESS", "NUMERIC", "END", "EXIT", "PROGRAM", "PERFORM", 
			"MOVE", "TO", "IF", "THEN", "ELSE", "ENDIF", "IS", "PIC", "COMMA", "PICXS", 
			"PICX", "PIC9S", "PIC9", "VALUE", "SPACE", "PROGRAMID", "REDEFINES", 
			"FILLER", "ID", "NUMBER", "CHAR", "INT", "WS", "EOL", "DOT", "MINUS", 
			"OPENBRACKET", "CLOSEBRACKET", "STRING", "UNKNOWN"
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


	public CobolLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Cobol.g4"; }

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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\29\u024e\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\4E\tE\4F\tF\4G\tG\4H\tH\4I"+
		"\tI\4J\tJ\4K\tK\4L\tL\4M\tM\4N\tN\4O\tO\4P\tP\4Q\tQ\4R\tR\3\2\3\2\3\2"+
		"\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4"+
		"\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3"+
		"\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7"+
		"\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3"+
		"\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3"+
		"\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3"+
		"\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r"+
		"\3\16\3\16\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17"+
		"\3\17\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\21"+
		"\3\22\3\22\3\22\3\22\3\22\3\22\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23"+
		"\3\23\3\23\3\23\3\24\3\24\3\24\3\24\3\24\3\24\3\24\5\24\u0165\n\24\3\25"+
		"\3\25\3\25\3\25\3\25\3\25\5\25\u016d\n\25\3\26\3\26\3\26\3\26\3\26\3\26"+
		"\3\26\3\26\3\27\3\27\3\27\3\27\3\30\3\30\3\30\3\30\3\30\3\31\3\31\3\31"+
		"\3\31\3\31\3\31\3\31\3\31\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\33"+
		"\3\33\3\33\3\33\3\33\3\34\3\34\3\34\3\35\3\35\3\35\3\36\3\36\3\36\3\36"+
		"\3\36\3\37\3\37\3\37\3\37\3\37\3 \3 \3 \3 \3 \3 \3 \3!\3!\3!\3\"\3\"\3"+
		"\"\3\"\3#\3#\3#\3#\3#\3#\3$\3$\6$\u01bb\n$\r$\16$\u01bc\3%\3%\3&\3&\6"+
		"&\u01c3\n&\r&\16&\u01c4\3\'\3\'\3(\3(\3(\3(\3(\3(\3)\3)\3)\3)\3)\3)\3"+
		"*\3*\3*\3*\3*\3*\3*\3*\3*\3*\3*\3+\3+\3+\3+\3+\3+\3+\3+\3+\3+\3,\3,\3"+
		",\3,\3,\3,\3,\3-\3-\3-\3-\6-\u01f5\n-\r-\16-\u01f6\3.\6.\u01fa\n.\r.\16"+
		".\u01fb\3/\3/\3\60\3\60\3\61\3\61\3\61\3\61\3\62\3\62\3\63\3\63\3\64\3"+
		"\64\3\65\3\65\3\66\3\66\3\67\3\67\7\67\u0212\n\67\f\67\16\67\u0215\13"+
		"\67\3\67\3\67\38\38\39\39\3:\3:\3;\3;\3<\3<\3=\3=\3>\3>\3?\3?\3@\3@\3"+
		"A\3A\3B\3B\3C\3C\3D\3D\3E\3E\3F\3F\3G\3G\3H\3H\3I\3I\3J\3J\3K\3K\3L\3"+
		"L\3M\3M\3N\3N\3O\3O\3P\3P\3Q\3Q\3R\3R\3\u0213\2S\3\3\5\4\7\5\t\6\13\7"+
		"\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25"+
		")\26+\27-\30/\31\61\32\63\33\65\34\67\359\36;\37= ?!A\"C#E$G%I&K\'M(O"+
		")Q*S+U,W-Y.[/]\60_\61a\62c\63e\64g\65i\66k\67m8o\2q\2s\2u\2w\2y\2{\2}"+
		"\2\177\2\u0081\2\u0083\2\u0085\2\u0087\2\u0089\2\u008b\2\u008d\2\u008f"+
		"\2\u0091\2\u0093\2\u0095\2\u0097\2\u0099\2\u009b\2\u009d\2\u009f\2\u00a1"+
		"\2\u00a39\3\2 \4\2C\\c|\3\2\62;\5\2\13\f\17\17\"\"\4\2\f\f\17\17\4\2C"+
		"Ccc\4\2DDdd\4\2EEee\4\2FFff\4\2GGgg\4\2HHhh\4\2IIii\4\2JJjj\4\2KKkk\4"+
		"\2LLll\4\2MMmm\4\2NNnn\4\2OOoo\4\2PPpp\4\2QQqq\4\2RRrr\4\2SSss\4\2TTt"+
		"t\4\2UUuu\4\2VVvv\4\2WWww\4\2XXxx\4\2YYyy\4\2ZZzz\4\2[[{{\4\2\\\\||\2"+
		"\u023c\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2"+
		"\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3"+
		"\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2"+
		"\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2"+
		"/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2"+
		"\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2"+
		"G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3"+
		"\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3\2\2\2\2]\3\2\2\2\2_\3\2\2"+
		"\2\2a\3\2\2\2\2c\3\2\2\2\2e\3\2\2\2\2g\3\2\2\2\2i\3\2\2\2\2k\3\2\2\2\2"+
		"m\3\2\2\2\2\u00a3\3\2\2\2\3\u00a5\3\2\2\2\5\u00b4\3\2\2\2\7\u00c0\3\2"+
		"\2\2\t\u00ce\3\2\2\2\13\u00dc\3\2\2\2\r\u00ea\3\2\2\2\17\u00f7\3\2\2\2"+
		"\21\u0104\3\2\2\2\23\u0109\3\2\2\2\25\u010e\3\2\2\2\27\u011e\3\2\2\2\31"+
		"\u0126\3\2\2\2\33\u0130\3\2\2\2\35\u0136\3\2\2\2\37\u013f\3\2\2\2!\u0147"+
		"\3\2\2\2#\u014c\3\2\2\2%\u0152\3\2\2\2\'\u0164\3\2\2\2)\u016c\3\2\2\2"+
		"+\u016e\3\2\2\2-\u0176\3\2\2\2/\u017a\3\2\2\2\61\u017f\3\2\2\2\63\u0187"+
		"\3\2\2\2\65\u018f\3\2\2\2\67\u0194\3\2\2\29\u0197\3\2\2\2;\u019a\3\2\2"+
		"\2=\u019f\3\2\2\2?\u01a4\3\2\2\2A\u01ab\3\2\2\2C\u01ae\3\2\2\2E\u01b2"+
		"\3\2\2\2G\u01b8\3\2\2\2I\u01be\3\2\2\2K\u01c0\3\2\2\2M\u01c6\3\2\2\2O"+
		"\u01c8\3\2\2\2Q\u01ce\3\2\2\2S\u01d4\3\2\2\2U\u01df\3\2\2\2W\u01e9\3\2"+
		"\2\2Y\u01f0\3\2\2\2[\u01f9\3\2\2\2]\u01fd\3\2\2\2_\u01ff\3\2\2\2a\u0201"+
		"\3\2\2\2c\u0205\3\2\2\2e\u0207\3\2\2\2g\u0209\3\2\2\2i\u020b\3\2\2\2k"+
		"\u020d\3\2\2\2m\u020f\3\2\2\2o\u0218\3\2\2\2q\u021a\3\2\2\2s\u021c\3\2"+
		"\2\2u\u021e\3\2\2\2w\u0220\3\2\2\2y\u0222\3\2\2\2{\u0224\3\2\2\2}\u0226"+
		"\3\2\2\2\177\u0228\3\2\2\2\u0081\u022a\3\2\2\2\u0083\u022c\3\2\2\2\u0085"+
		"\u022e\3\2\2\2\u0087\u0230\3\2\2\2\u0089\u0232\3\2\2\2\u008b\u0234\3\2"+
		"\2\2\u008d\u0236\3\2\2\2\u008f\u0238\3\2\2\2\u0091\u023a\3\2\2\2\u0093"+
		"\u023c\3\2\2\2\u0095\u023e\3\2\2\2\u0097\u0240\3\2\2\2\u0099\u0242\3\2"+
		"\2\2\u009b\u0244\3\2\2\2\u009d\u0246\3\2\2\2\u009f\u0248\3\2\2\2\u00a1"+
		"\u024a\3\2\2\2\u00a3\u024c\3\2\2\2\u00a5\u00a6\5\177@\2\u00a6\u00a7\5"+
		"u;\2\u00a7\u00a8\5w<\2\u00a8\u00a9\5\u0089E\2\u00a9\u00aa\5\u0095K\2\u00aa"+
		"\u00ab\5\177@\2\u00ab\u00ac\5y=\2\u00ac\u00ad\5\177@\2\u00ad\u00ae\5s"+
		":\2\u00ae\u00af\5o8\2\u00af\u00b0\5\u0095K\2\u00b0\u00b1\5\177@\2\u00b1"+
		"\u00b2\5\u008bF\2\u00b2\u00b3\5\u0089E\2\u00b3\4\3\2\2\2\u00b4\u00b5\5"+
		"w<\2\u00b5\u00b6\5\u0089E\2\u00b6\u00b7\5\u0099M\2\u00b7\u00b8\5\177@"+
		"\2\u00b8\u00b9\5\u0091I\2\u00b9\u00ba\5\u008bF\2\u00ba\u00bb\5\u0089E"+
		"\2\u00bb\u00bc\5\u0087D\2\u00bc\u00bd\5w<\2\u00bd\u00be\5\u0089E\2\u00be"+
		"\u00bf\5\u0095K\2\u00bf\6\3\2\2\2\u00c0\u00c1\5s:\2\u00c1\u00c2\5\u008b"+
		"F\2\u00c2\u00c3\5\u0089E\2\u00c3\u00c4\5y=\2\u00c4\u00c5\5\177@\2\u00c5"+
		"\u00c6\5{>\2\u00c6\u00c7\5\u0097L\2\u00c7\u00c8\5\u0091I\2\u00c8\u00c9"+
		"\5o8\2\u00c9\u00ca\5\u0095K\2\u00ca\u00cb\5\177@\2\u00cb\u00cc\5\u008b"+
		"F\2\u00cc\u00cd\5\u0089E\2\u00cd\b\3\2\2\2\u00ce\u00cf\5\u0093J\2\u00cf"+
		"\u00d0\5\u008dG\2\u00d0\u00d1\5w<\2\u00d1\u00d2\5s:\2\u00d2\u00d3\5\177"+
		"@\2\u00d3\u00d4\5o8\2\u00d4\u00d5\5\u0085C\2\u00d5\u00d6\5g\64\2\u00d6"+
		"\u00d7\5\u0089E\2\u00d7\u00d8\5o8\2\u00d8\u00d9\5\u0087D\2\u00d9\u00da"+
		"\5w<\2\u00da\u00db\5\u0093J\2\u00db\n\3\2\2\2\u00dc\u00dd\5u;\2\u00dd"+
		"\u00de\5w<\2\u00de\u00df\5s:\2\u00df\u00e0\5\177@\2\u00e0\u00e1\5\u0087"+
		"D\2\u00e1\u00e2\5o8\2\u00e2\u00e3\5\u0085C\2\u00e3\u00e4\5g\64\2\u00e4"+
		"\u00e5\5\u008dG\2\u00e5\u00e6\5\u008bF\2\u00e6\u00e7\5\177@\2\u00e7\u00e8"+
		"\5\u0089E\2\u00e8\u00e9\5\u0095K\2\u00e9\f\3\2\2\2\u00ea\u00eb\5\177@"+
		"\2\u00eb\u00ec\5\u0089E\2\u00ec\u00ed\5\u008dG\2\u00ed\u00ee\5\u0097L"+
		"\2\u00ee\u00ef\5\u0095K\2\u00ef\u00f0\5g\64\2\u00f0\u00f1\5\u008bF\2\u00f1"+
		"\u00f2\5\u0097L\2\u00f2\u00f3\5\u0095K\2\u00f3\u00f4\5\u008dG\2\u00f4"+
		"\u00f5\5\u0097L\2\u00f5\u00f6\5\u0095K\2\u00f6\16\3\2\2\2\u00f7\u00f8"+
		"\5y=\2\u00f8\u00f9\5\177@\2\u00f9\u00fa\5\u0085C\2\u00fa\u00fb\5w<\2\u00fb"+
		"\u00fc\5g\64\2\u00fc\u00fd\5s:\2\u00fd\u00fe\5\u008bF\2\u00fe\u00ff\5"+
		"\u0089E\2\u00ff\u0100\5\u0095K\2\u0100\u0101\5\u0091I\2\u0101\u0102\5"+
		"\u008bF\2\u0102\u0103\5\u0085C\2\u0103\20\3\2\2\2\u0104\u0105\5u;\2\u0105"+
		"\u0106\5o8\2\u0106\u0107\5\u0095K\2\u0107\u0108\5o8\2\u0108\22\3\2\2\2"+
		"\u0109\u010a\5y=\2\u010a\u010b\5\177@\2\u010b\u010c\5\u0085C\2\u010c\u010d"+
		"\5w<\2\u010d\24\3\2\2\2\u010e\u010f\5\u009bN\2\u010f\u0110\5\u008bF\2"+
		"\u0110\u0111\5\u0091I\2\u0111\u0112\5\u0083B\2\u0112\u0113\5\177@\2\u0113"+
		"\u0114\5\u0089E\2\u0114\u0115\5{>\2\u0115\u0116\5g\64\2\u0116\u0117\5"+
		"\u0093J\2\u0117\u0118\5\u0095K\2\u0118\u0119\5\u008bF\2\u0119\u011a\5"+
		"\u0091I\2\u011a\u011b\5o8\2\u011b\u011c\5{>\2\u011c\u011d\5w<\2\u011d"+
		"\26\3\2\2\2\u011e\u011f\5\u0085C\2\u011f\u0120\5\177@\2\u0120\u0121\5"+
		"\u0089E\2\u0121\u0122\5\u0083B\2\u0122\u0123\5o8\2\u0123\u0124\5{>\2\u0124"+
		"\u0125\5w<\2\u0125\30\3\2\2\2\u0126\u0127\5\u008dG\2\u0127\u0128\5\u0091"+
		"I\2\u0128\u0129\5\u008bF\2\u0129\u012a\5s:\2\u012a\u012b\5w<\2\u012b\u012c"+
		"\5u;\2\u012c\u012d\5\u0097L\2\u012d\u012e\5\u0091I\2\u012e\u012f\5w<\2"+
		"\u012f\32\3\2\2\2\u0130\u0131\5\u0097L\2\u0131\u0132\5\u0093J\2\u0132"+
		"\u0133\5\177@\2\u0133\u0134\5\u0089E\2\u0134\u0135\5{>\2\u0135\34\3\2"+
		"\2\2\u0136\u0137\5u;\2\u0137\u0138\5\177@\2\u0138\u0139\5\u0099M\2\u0139"+
		"\u013a\5\177@\2\u013a\u013b\5\u0093J\2\u013b\u013c\5\177@\2\u013c\u013d"+
		"\5\u008bF\2\u013d\u013e\5\u0089E\2\u013e\36\3\2\2\2\u013f\u0140\5\u0093"+
		"J\2\u0140\u0141\5w<\2\u0141\u0142\5s:\2\u0142\u0143\5\u0095K\2\u0143\u0144"+
		"\5\177@\2\u0144\u0145\5\u008bF\2\u0145\u0146\5\u0089E\2\u0146 \3\2\2\2"+
		"\u0147\u0148\5\u0095K\2\u0148\u0149\5\u0091I\2\u0149\u014a\5\u0097L\2"+
		"\u014a\u014b\5w<\2\u014b\"\3\2\2\2\u014c\u014d\5y=\2\u014d\u014e\5o8\2"+
		"\u014e\u014f\5\u0085C\2\u014f\u0150\5\u0093J\2\u0150\u0151\5w<\2\u0151"+
		"$\3\2\2\2\u0152\u0153\5\177@\2\u0153\u0154\5\u0089E\2\u0154\u0155\5\177"+
		"@\2\u0155\u0156\5\u0095K\2\u0156\u0157\5\177@\2\u0157\u0158\5o8\2\u0158"+
		"\u0159\5\u0085C\2\u0159\u015a\5\177@\2\u015a\u015b\5\u00a1Q\2\u015b\u015c"+
		"\5w<\2\u015c&\3\2\2\2\u015d\u015e\5w<\2\u015e\u015f\5\u008fH\2\u015f\u0160"+
		"\5\u0097L\2\u0160\u0161\5o8\2\u0161\u0162\5\u0085C\2\u0162\u0165\3\2\2"+
		"\2\u0163\u0165\7?\2\2\u0164\u015d\3\2\2\2\u0164\u0163\3\2\2\2\u0165(\3"+
		"\2\2\2\u0166\u0167\5\u0085C\2\u0167\u0168\5w<\2\u0168\u0169\5\u0093J\2"+
		"\u0169\u016a\5\u0093J\2\u016a\u016d\3\2\2\2\u016b\u016d\7>\2\2\u016c\u0166"+
		"\3\2\2\2\u016c\u016b\3\2\2\2\u016d*\3\2\2\2\u016e\u016f\5\u0089E\2\u016f"+
		"\u0170\5\u0097L\2\u0170\u0171\5\u0087D\2\u0171\u0172\5w<\2\u0172\u0173"+
		"\5\u0091I\2\u0173\u0174\5\177@\2\u0174\u0175\5s:\2\u0175,\3\2\2\2\u0176"+
		"\u0177\5w<\2\u0177\u0178\5\u0089E\2\u0178\u0179\5u;\2\u0179.\3\2\2\2\u017a"+
		"\u017b\5w<\2\u017b\u017c\5\u009dO\2\u017c\u017d\5\177@\2\u017d\u017e\5"+
		"\u0095K\2\u017e\60\3\2\2\2\u017f\u0180\5\u008dG\2\u0180\u0181\5\u0091"+
		"I\2\u0181\u0182\5\u008bF\2\u0182\u0183\5{>\2\u0183\u0184\5\u0091I\2\u0184"+
		"\u0185\5o8\2\u0185\u0186\5\u0087D\2\u0186\62\3\2\2\2\u0187\u0188\5\u008d"+
		"G\2\u0188\u0189\5w<\2\u0189\u018a\5\u0091I\2\u018a\u018b\5y=\2\u018b\u018c"+
		"\5\u008bF\2\u018c\u018d\5\u0091I\2\u018d\u018e\5\u0087D\2\u018e\64\3\2"+
		"\2\2\u018f\u0190\5\u0087D\2\u0190\u0191\5\u008bF\2\u0191\u0192\5\u0099"+
		"M\2\u0192\u0193\5w<\2\u0193\66\3\2\2\2\u0194\u0195\5\u0095K\2\u0195\u0196"+
		"\5\u008bF\2\u01968\3\2\2\2\u0197\u0198\5\177@\2\u0198\u0199\5y=\2\u0199"+
		":\3\2\2\2\u019a\u019b\5\u0095K\2\u019b\u019c\5}?\2\u019c\u019d\5w<\2\u019d"+
		"\u019e\5\u0089E\2\u019e<\3\2\2\2\u019f\u01a0\5w<\2\u01a0\u01a1\5\u0085"+
		"C\2\u01a1\u01a2\5\u0093J\2\u01a2\u01a3\5w<\2\u01a3>\3\2\2\2\u01a4\u01a5"+
		"\5w<\2\u01a5\u01a6\5\u0089E\2\u01a6\u01a7\5u;\2\u01a7\u01a8\5g\64\2\u01a8"+
		"\u01a9\5\177@\2\u01a9\u01aa\5y=\2\u01aa@\3\2\2\2\u01ab\u01ac\5\177@\2"+
		"\u01ac\u01ad\5\u0093J\2\u01adB\3\2\2\2\u01ae\u01af\5\u008dG\2\u01af\u01b0"+
		"\5\177@\2\u01b0\u01b1\5s:\2\u01b1D\3\2\2\2\u01b2\u01b3\5s:\2\u01b3\u01b4"+
		"\5\u008bF\2\u01b4\u01b5\5\u0087D\2\u01b5\u01b6\5\u0087D\2\u01b6\u01b7"+
		"\5o8\2\u01b7F\3\2\2\2\u01b8\u01ba\5I%\2\u01b9\u01bb\5I%\2\u01ba\u01b9"+
		"\3\2\2\2\u01bb\u01bc\3\2\2\2\u01bc\u01ba\3\2\2\2\u01bc\u01bd\3\2\2\2\u01bd"+
		"H\3\2\2\2\u01be\u01bf\5\u009dO\2\u01bfJ\3\2\2\2\u01c0\u01c2\5M\'\2\u01c1"+
		"\u01c3\5M\'\2\u01c2\u01c1\3\2\2\2\u01c3\u01c4\3\2\2\2\u01c4\u01c2\3\2"+
		"\2\2\u01c4\u01c5\3\2\2\2\u01c5L\3\2\2\2\u01c6\u01c7\7;\2\2\u01c7N\3\2"+
		"\2\2\u01c8\u01c9\5\u0099M\2\u01c9\u01ca\5o8\2\u01ca\u01cb\5\u0085C\2\u01cb"+
		"\u01cc\5\u0097L\2\u01cc\u01cd\5w<\2\u01cdP\3\2\2\2\u01ce\u01cf\5\u0093"+
		"J\2\u01cf\u01d0\5\u008dG\2\u01d0\u01d1\5o8\2\u01d1\u01d2\5s:\2\u01d2\u01d3"+
		"\5w<\2\u01d3R\3\2\2\2\u01d4\u01d5\5\u008dG\2\u01d5\u01d6\5\u0091I\2\u01d6"+
		"\u01d7\5\u008bF\2\u01d7\u01d8\5{>\2\u01d8\u01d9\5\u0091I\2\u01d9\u01da"+
		"\5o8\2\u01da\u01db\5\u0087D\2\u01db\u01dc\5g\64\2\u01dc\u01dd\5\177@\2"+
		"\u01dd\u01de\5u;\2\u01deT\3\2\2\2\u01df\u01e0\5\u0091I\2\u01e0\u01e1\5"+
		"w<\2\u01e1\u01e2\5u;\2\u01e2\u01e3\5w<\2\u01e3\u01e4\5y=\2\u01e4\u01e5"+
		"\5\177@\2\u01e5\u01e6\5\u0089E\2\u01e6\u01e7\5w<\2\u01e7\u01e8\5\u0093"+
		"J\2\u01e8V\3\2\2\2\u01e9\u01ea\5y=\2\u01ea\u01eb\5\177@\2\u01eb\u01ec"+
		"\5\u0085C\2\u01ec\u01ed\5\u0085C\2\u01ed\u01ee\5w<\2\u01ee\u01ef\5\u0091"+
		"I\2\u01efX\3\2\2\2\u01f0\u01f4\5]/\2\u01f1\u01f5\5]/\2\u01f2\u01f5\5_"+
		"\60\2\u01f3\u01f5\5g\64\2\u01f4\u01f1\3\2\2\2\u01f4\u01f2\3\2\2\2\u01f4"+
		"\u01f3\3\2\2\2\u01f5\u01f6\3\2\2\2\u01f6\u01f4\3\2\2\2\u01f6\u01f7\3\2"+
		"\2\2\u01f7Z\3\2\2\2\u01f8\u01fa\5_\60\2\u01f9\u01f8\3\2\2\2\u01fa\u01fb"+
		"\3\2\2\2\u01fb\u01f9\3\2\2\2\u01fb\u01fc\3\2\2\2\u01fc\\\3\2\2\2\u01fd"+
		"\u01fe\t\2\2\2\u01fe^\3\2\2\2\u01ff\u0200\t\3\2\2\u0200`\3\2\2\2\u0201"+
		"\u0202\t\4\2\2\u0202\u0203\3\2\2\2\u0203\u0204\b\61\2\2\u0204b\3\2\2\2"+
		"\u0205\u0206\t\5\2\2\u0206d\3\2\2\2\u0207\u0208\7\60\2\2\u0208f\3\2\2"+
		"\2\u0209\u020a\7/\2\2\u020ah\3\2\2\2\u020b\u020c\7*\2\2\u020cj\3\2\2\2"+
		"\u020d\u020e\7+\2\2\u020el\3\2\2\2\u020f\u0213\7$\2\2\u0210\u0212\13\2"+
		"\2\2\u0211\u0210\3\2\2\2\u0212\u0215\3\2\2\2\u0213\u0214\3\2\2\2\u0213"+
		"\u0211\3\2\2\2\u0214\u0216\3\2\2\2\u0215\u0213\3\2\2\2\u0216\u0217\7$"+
		"\2\2\u0217n\3\2\2\2\u0218\u0219\t\6\2\2\u0219p\3\2\2\2\u021a\u021b\t\7"+
		"\2\2\u021br\3\2\2\2\u021c\u021d\t\b\2\2\u021dt\3\2\2\2\u021e\u021f\t\t"+
		"\2\2\u021fv\3\2\2\2\u0220\u0221\t\n\2\2\u0221x\3\2\2\2\u0222\u0223\t\13"+
		"\2\2\u0223z\3\2\2\2\u0224\u0225\t\f\2\2\u0225|\3\2\2\2\u0226\u0227\t\r"+
		"\2\2\u0227~\3\2\2\2\u0228\u0229\t\16\2\2\u0229\u0080\3\2\2\2\u022a\u022b"+
		"\t\17\2\2\u022b\u0082\3\2\2\2\u022c\u022d\t\20\2\2\u022d\u0084\3\2\2\2"+
		"\u022e\u022f\t\21\2\2\u022f\u0086\3\2\2\2\u0230\u0231\t\22\2\2\u0231\u0088"+
		"\3\2\2\2\u0232\u0233\t\23\2\2\u0233\u008a\3\2\2\2\u0234\u0235\t\24\2\2"+
		"\u0235\u008c\3\2\2\2\u0236\u0237\t\25\2\2\u0237\u008e\3\2\2\2\u0238\u0239"+
		"\t\26\2\2\u0239\u0090\3\2\2\2\u023a\u023b\t\27\2\2\u023b\u0092\3\2\2\2"+
		"\u023c\u023d\t\30\2\2\u023d\u0094\3\2\2\2\u023e\u023f\t\31\2\2\u023f\u0096"+
		"\3\2\2\2\u0240\u0241\t\32\2\2\u0241\u0098\3\2\2\2\u0242\u0243\t\33\2\2"+
		"\u0243\u009a\3\2\2\2\u0244\u0245\t\34\2\2\u0245\u009c\3\2\2\2\u0246\u0247"+
		"\t\35\2\2\u0247\u009e\3\2\2\2\u0248\u0249\t\36\2\2\u0249\u00a0\3\2\2\2"+
		"\u024a\u024b\t\37\2\2\u024b\u00a2\3\2\2\2\u024c\u024d\13\2\2\2\u024d\u00a4"+
		"\3\2\2\2\13\2\u0164\u016c\u01bc\u01c4\u01f4\u01f6\u01fb\u0213\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}