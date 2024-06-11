package ua.nure.paymentservice.service;

import com.stripe.exception.StripeException;
import com.stripe.model.Subscription;
import com.stripe.model.SubscriptionCollection;
import com.stripe.param.SubscriptionCreateParams;
import com.stripe.param.SubscriptionListParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.nure.paymentservice.request.ChargeRequest;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StripeSubscriptionService {

  public Subscription createSubscription(ChargeRequest chargeRequest) {
    try {
      SubscriptionCreateParams params = SubscriptionCreateParams.builder()
              .addItem(SubscriptionCreateParams.Item.builder()
                      .setPlan(chargeRequest.getPlanId())
                      .build())
              .setCustomer(chargeRequest.getCustomerId())
              .build();

        return Subscription.create(params);
    } catch (StripeException e) {
      log.error(e.getMessage());
      return null;
    }
  }

    public List<Subscription> retrieveSubscriptions(String customerId) {
        try {
            SubscriptionCollection subscriptions = Subscription.list(SubscriptionListParams.builder().setCustomer(customerId).build());
            return subscriptions.getData();
        } catch (StripeException e) {
            log.error(e.getMessage());
            return null;
        }
    }

}


