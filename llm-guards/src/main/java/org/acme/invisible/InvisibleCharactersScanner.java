package org.acme.invisible;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class InvisibleCharactersScanner {

    public String removeInvisibleCharacters(String input) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);

            // Check the Unicode category of each character
            int category = Character.getType(ch);
            if (category != Character.FORMAT &&   // Cf: Format
                category != Character.CONTROL &&  // Cc: Control
                category != Character.PRIVATE_USE && // Co: Private use
                category != Character.UNASSIGNED) { // Cn: Unassigned
                result.append(ch);
            }
        }

        return result.toString();
    }

}
