package com.epam.springadvanced.utils;

import org.springframework.ui.ModelMap;

public final class ControllerUtils
{
    private ControllerUtils()
    {
    }

    public static void addResultToModel(final ModelMap model, Object foundObject)
    {
        String result = foundObject != null ? foundObject.toString() : "not found";
        model.addAttribute("searchResult", result);
    }
}
