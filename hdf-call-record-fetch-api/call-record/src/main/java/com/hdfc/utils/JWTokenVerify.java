/* Decompiler 4ms, total 320ms, lines 37 */
package com.hdfc.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import javax.xml.bind.DatatypeConverter;
import org.json.JSONException;
import org.json.JSONObject;

public class JWTokenVerify {
   public static JSONObject verifyJWT(String xAccessToken, String secretKey) {
      JSONObject response = new JSONObject();

      try {
         Claims claims = (Claims)Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(secretKey)).parseClaimsJws(xAccessToken).getBody();
         if (claims != null && !claims.isEmpty()) {
            response.put("claims", claims);
            response.put("message", "valid");
            response.put("statusCode", 200);
         } else {
            response.put("message", "invalid");
            response.put("statusCode", 400);
         }
      } catch (Exception var6) {
         Exception e = var6;

         try {
            response.put("message", e.getMessage());
            response.put("statusCode", 400);
         } catch (JSONException var5) {
            var5.printStackTrace();
         }
      }

      return response;
   }
}
