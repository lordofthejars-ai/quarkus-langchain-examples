package org.acme;

import io.quarkiverse.langchain4j.guardrails.InputGuardrail;
import io.quarkiverse.langchain4j.guardrails.InputGuardrailParams;
import io.quarkiverse.langchain4j.guardrails.InputGuardrailResult;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.competitors.CompetitorsModel;

@ApplicationScoped
public class CompetitorsGuardRail implements InputGuardrail {

    @Inject
    CompetitorsModel competitorsModel;

    @Override
    public InputGuardrailResult validate(InputGuardrailParams inputGuardrailParams) {
        String prompt = (String) inputGuardrailParams.variables().get("story");
        
        return competitorsModel.isCompetitorCited(prompt) ? success() : failure("Competitor cited");

    }

}
