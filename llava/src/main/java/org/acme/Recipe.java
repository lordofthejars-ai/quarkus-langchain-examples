package org.acme;

import java.util.List;

public record Recipe(String title, List<String> ingredients, List<String> steps) {
}
