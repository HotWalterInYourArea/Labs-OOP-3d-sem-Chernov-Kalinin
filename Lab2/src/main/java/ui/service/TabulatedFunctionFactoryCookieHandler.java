package ui.service;

import functions.factory.ArrayTabulatedFunctionFactory;
import functions.factory.FactoryType;
import functions.factory.LinkedListTabulatedFunctionFactory;
import functions.factory.TabulatedFunctionFactory;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.Context;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;

public class TabulatedFunctionFactoryCookieHandler {
        private final Map<FactoryType, TabulatedFunctionFactory> factories;

        public TabulatedFunctionFactoryCookieHandler() {
            factories = new HashMap<>();
            factories.put(FactoryType.ARRAY, new ArrayTabulatedFunctionFactory());
            factories.put(FactoryType.LINKEDLIST, new LinkedListTabulatedFunctionFactory());
        }
        public TabulatedFunctionFactory determineFabric(@Context HttpServletRequest request, @Context HttpServletResponse response) {
            Cookie[] cookies = request.getCookies();
            if(isNull(cookies)){
                saveDefaultFactoryTypeCookieIfNotExists(response);
                return factories.get(FactoryType.ARRAY);
            }
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("fabricType")) {
                    return factories.get(FactoryType.valueOf(cookie.getValue()));
                }
            }
            saveDefaultFactoryTypeCookieIfNotExists(response);
            return factories.get(FactoryType.ARRAY);
        }
        private void saveDefaultFactoryTypeCookieIfNotExists(@Context HttpServletResponse response) {
            Cookie cookie = new Cookie("fabricType", FactoryType.ARRAY.name());
            cookie.setPath("/");
            cookie.setHttpOnly(false);
            response.addCookie(cookie);
        }
}
