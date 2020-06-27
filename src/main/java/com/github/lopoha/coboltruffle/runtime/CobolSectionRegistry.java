package com.github.lopoha.coboltruffle.runtime;

import com.github.lopoha.coboltruffle.CobolLanguage;
import com.github.lopoha.coboltruffle.parser.NotImplementedException;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.source.Source;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public final class CobolSectionRegistry {

  private final CobolLanguage language;
  private final SectionsObject functionsObject = new SectionsObject();

  public CobolSectionRegistry(CobolLanguage language) {
    this.language = language;
  }

  /**
   * Returns the canonical {@link CobolSection} object for the given name. If it does not exist yet,
   * it is created.
   */
  public CobolSection lookup(String name, boolean createIfNotPresent) {
    CobolSection result = functionsObject.functions.get(name);
    if (result == null && createIfNotPresent) {
      result = new CobolSection(language, name);
      functionsObject.functions.put(name, result);
    }
    return result;
  }

  /**
   * // todo: replace stolen comment from sllanguage
   * Associates the {@link CobolSection} with the given name with the given implementation root
   * node. If the function did not exist before, it defines the function. If the function existed
   * before, it redefines the function and the old implementation is discarded.
   */
  public CobolSection register(String name, RootCallTarget callTarget) {
    CobolSection function = lookup(name, true);
    function.setCallTarget(callTarget);
    return function;
  }

  /**
   * // todo: replace stolen comment from sllanguage
   * Associates all the given {@link CobolSection} the given implementation root nodes.
   * If the function did not exist before, it defines the function. If the function existed
   * before, it redefines the function and the old implementation is discarded.
   */

  public void register(Map<String, RootCallTarget> newFunctions) {
    for (Map.Entry<String, RootCallTarget> entry : newFunctions.entrySet()) {
      register(entry.getKey(), entry.getValue());
    }
  }

  public void register(Source newFunctions) {
    throw new NotImplementedException();
    //register(SimpleLanguageParser.parseCobol(language, newFunctions));
  }

  public CobolSection getFunction(String name) {
    return functionsObject.functions.get(name);
  }

  /**
   * Returns the sorted list of all functions, for printing purposes only.
   */
  public List<CobolSection> getFunctions() {
    List<CobolSection> result = new ArrayList<>(functionsObject.functions.values());
    result.sort(Comparator.comparing(CobolSection::toString));
    return result;
  }

  public TruffleObject getFunctionsObject() {
    return functionsObject;
  }

}
