package com.suraj.dypatilapi.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.suraj.dypatilapi.dto.RequestDto;
import com.suraj.dypatilapi.service.BFHLService;

@Service
public class BFHLServiceImpl implements BFHLService {

    @Override
    public Map<String, Object> process(RequestDto request) {

        List<String> oddNumbers = new ArrayList<>();
        List<String> evenNumbers = new ArrayList<>();
        List<String> alphabets = new ArrayList<>();
        List<String> specialCharacters = new ArrayList<>();

        double sum = 0;
        Double largest = null;
        Double smallest = null;

        Set<String> uniqueValues = new LinkedHashSet<>();
        boolean containsDuplicates = false;

        // Remove nulls, empty strings and detect duplicates
        for (String item : request.getData()) {

            if (item == null || item.trim().isEmpty()) {
                continue;
            }

            item = item.trim();

            if (!uniqueValues.add(item)) {
                containsDuplicates = true;
            }
        }

        // Process unique values
        for (String item : uniqueValues) {

            // Handle integers, negatives and decimals
            if (item.matches("-?\\d+(\\.\\d+)?")) {

                double num = Double.parseDouble(item);

                sum += num;

                // Odd/Even only for integers
                if (num % 1 == 0) {

                    if (((int) num) % 2 == 0) {
                        evenNumbers.add(item);
                    } else {
                        oddNumbers.add(item);
                    }
                }

                if (largest == null || num > largest) {
                    largest = num;
                }

                if (smallest == null || num < smallest) {
                    smallest = num;
                }
            }

            // Only alphabets
            else if (item.matches("[a-zA-Z]+")) {

                alphabets.add(item.toUpperCase());
            }

            // Alphanumeric values
            else if (item.matches(".*[a-zA-Z].*") &&
                     item.matches(".*\\d.*")) {

                String letters = item.replaceAll("[^a-zA-Z]", "");
                String numbers = item.replaceAll("[^0-9]", "");

                if (!letters.isEmpty()) {
                    alphabets.add(letters.toUpperCase());
                }

                if (!numbers.isEmpty()) {

                    int num = Integer.parseInt(numbers);

                    sum += num;

                    if (num % 2 == 0) {
                        evenNumbers.add(String.valueOf(num));
                    } else {
                        oddNumbers.add(String.valueOf(num));
                    }

                    if (largest == null || num > largest) {
                        largest = (double) num;
                    }

                    if (smallest == null || num < smallest) {
                        smallest = (double) num;
                    }
                }
            }

            // Special characters
            else {

                specialCharacters.add(item);
            }
        }

        Map<String, Object> response = new HashMap<>();

        response.put("is_success", true);
        response.put("odd_numbers", oddNumbers);
        response.put("even_numbers", evenNumbers);
        response.put("alphabets", alphabets);
        response.put("special_characters", specialCharacters);

        response.put("sum", String.valueOf(sum));

        response.put("largest_number",
                largest == null ? "" : String.valueOf(largest));

        response.put("smallest_number",
                smallest == null ? "" : String.valueOf(smallest));

        response.put("alphabet_count", alphabets.size());

        response.put("number_count",
                oddNumbers.size() + evenNumbers.size());

        response.put("special_character_count",
                specialCharacters.size());

        response.put("contains_duplicates",
                containsDuplicates);

        response.put("unique_element_count",
                uniqueValues.size());

        return response;
    }
}