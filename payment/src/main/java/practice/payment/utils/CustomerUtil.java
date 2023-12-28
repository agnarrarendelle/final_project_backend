package practice.payment.utils;

import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.CustomerSearchResult;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.CustomerSearchParams;

import java.util.List;

public class CustomerUtil {
    public static Customer findCustomerByEmail(String email) throws StripeException{
        CustomerSearchParams params =
                CustomerSearchParams
                        .builder()
                        .setQuery(String.format("email:'%s'", email))
                        .build();

        CustomerSearchResult result = Customer.search(params);
        List<Customer> data = result.getData();
        return data.isEmpty() ? null : data.get(0);
    }

    public static Customer findOrCreateCustomer(String email, String name) throws StripeException {
        CustomerSearchParams params =
                CustomerSearchParams
                        .builder()
                        .setQuery("email:'" + email + "'")
                        .build();

        CustomerSearchResult result = Customer.search(params);

        Customer customer;

        // If no existing customer was found, create a new record
        if (result.getData().isEmpty()) {

            CustomerCreateParams customerCreateParams = CustomerCreateParams.builder()
                    .setName(name)
                    .setEmail(email)
                    .build();

            customer = Customer.create(customerCreateParams);
        } else {
            customer = result.getData().get(0);
        }

        return customer;
    }
}

