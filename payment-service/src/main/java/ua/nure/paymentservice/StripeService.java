package ua.nure.paymentservice;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StripeService {

  public StripeService(@Value("${STRIPE_SECRET_KEY}") String secretKey) {
     Stripe.apiKey = secretKey;
  }

  public Session createSession() throws StripeException {
    List<Object> paymentMethodTypes =
        new ArrayList<>();
    paymentMethodTypes.add("card");
    
    SessionCreateParams params =
        SessionCreateParams.builder()
            .setSuccessUrl("http://localhost:8080/success?session_id={CHECKOUT_SESSION_ID}")
            .setCancelUrl("http://localhost:8080/cancel")
            .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
            .setMode(SessionCreateParams.Mode.SUBSCRIPTION)
            .addLineItem(
                    SessionCreateParams.LineItem.builder()
                            .setPriceData(
                                    SessionCreateParams.LineItem.PriceData.builder()
                                            .setCurrency("usd")
                                            .setProduct("Preasd") // The product you're selling
                                            .setUnitAmount(50L) // use minor units, like cents for USD
                                            .build())
            .setQuantity(1L)
            .build())
            .build(); 
      
    return Session.create(params);
  }
}