package org.acme;

import ai.djl.ndarray.NDArray;
import ai.djl.ndarray.NDList;
import ai.djl.translate.NoBatchifyTranslator;
import ai.djl.translate.TranslatorContext;

public class PromptInjectionTranslator implements NoBatchifyTranslator<String, Boolean> {
    @Override
    public Boolean processOutput(TranslatorContext ctx, NDList list) throws Exception {
        System.out.println(list);
        return false;
    }

    @Override
    public NDList processInput(TranslatorContext ctx, String input) throws Exception {
        final NDArray stringNdArray = ctx.getNDManager().create(input);
        stringNdArray.setName("input_ids");
        return new NDList(stringNdArray);
    }
}
