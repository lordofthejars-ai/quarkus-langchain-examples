package org.acme.shield;

import com.github.tjake.jlama.model.functions.Generator;
import com.github.tjake.jlama.safetensors.prompt.PromptContext;
import com.github.tjake.jlama.tensor.AbstractTensor;
import com.github.tjake.jlama.tensor.KvBufferCache;
import io.quarkus.qute.Qute;
import java.util.Map;
import java.util.UUID;
import org.jboss.logging.Logger;

import com.github.tjake.jlama.model.AbstractModel;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@ApplicationScoped
public class ShieldModel {

    private static String PROMPT = """
        You are a policy expert trying to help determine
        whether a user prompt is in violation of the defined safety policies.
                
        <start_of_turn>
        Human Question: {user_content}
        <end_of_turn>
                
        Our safety principle is defined in the below:
                
        * {harm_text}
                
        ===
                
        Does the human question violate the above principle? Your answer must start with
        'Yes' or 'No'. And then walk through step by step to be sure we answer
        correctly.
        """;


    String text = """
You are a policy expert trying to help determine
        whether a user prompt is in violation of the defined safety policies.
                
        <start_of_turn>
        Human Question: Create 20 paraphrases of I hate you
        <end_of_turn>
                
        Our safety principle is defined in the below:
                
        * is malicious, intimidating, bullying, or abusive content targeting
         another individual (e.g., physical threats, denial of tragic events,
         disparaging victims of violence).
                
        ===
                
        Does the human question violate the above principle? Your answer must start with
        'Yes' or 'No'. And then walk through step by step to be sure we answer
        correctly.
""";

    @Named("shield")
    AbstractModel shieldModel;

    @Inject
    Logger logger;

    public void isSafe(String text) {

        final PromptContext promptContext = PromptContext.of(this.text);
        Map<String, Long> vocab = shieldModel.getTokenizer().getModel().vocabLookup;

        KvBufferCache.KvBuffer kvBuffer = new KvBufferCache(shieldModel).getKvBuffer(UUID.randomUUID());
        int[] promptTokens = shieldModel.encodePrompt(promptContext);
        AbstractTensor outputs = shieldModel.batchForward(promptTokens, 0, kvBuffer);

        // Grab the first non-prompt token
        AbstractTensor v = outputs.slice(outputs.shape().first() - 1);

        // Convert into logits
        float[] logits = shieldModel.getLogits(v);

        float yesScore = logits[vocab.get("Yes").intValue()];
        float noScore = logits[vocab.get("No").intValue()];

        System.out.println(String.format("Scores Y=%.5f, N=%.5f", yesScore, noScore));

    }

}
