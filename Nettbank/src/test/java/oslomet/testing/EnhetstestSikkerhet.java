package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.mock.web.MockHttpSession;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestSikkerhet {

    @InjectMocks
    // denne skal testes
    private Sikkerhet sikkerhetsController;

    @Mock
    // denne skal Mock'es
    private BankRepository repository;

    @Mock
    // denne skal Mock'es
    private MockHttpSession session;

    @Test
    public void test_sjekkLoggetInnOK() {

        when(repository.sjekkLoggInn(anyString(), anyString())).thenReturn("OK");

        Map<String, Object> attributes = new HashMap<String, Object>();

        doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                String key = (String) invocation.getArguments()[0];
                Object value = invocation.getArguments()[1];
                attributes.put(key, value);
                return null;
            }
        }).when(session).setAttribute(anyString(), any());

        session.setAttribute("Innlogget", "12345678901");

        String resultat = sikkerhetsController.sjekkLoggInn("12345678901", "HeiHeiHei");

        assertEquals("OK", resultat);
    }

    @Test
    public void test_sjekkfeilPW() {

        String passord = "12345";

        String resultat = sikkerhetsController.sjekkLoggInn("12345678910", passord);

        assertEquals("Feil i passord", resultat);
    }

    @Test
    public void test_sjekkfeilPN() {

        String personnummer = "12345";

        String resultat = sikkerhetsController.sjekkLoggInn(personnummer, "HeiHei");

        assertEquals("Feil i personnummer", resultat);
    }

    @Test
    public void test_sjekkLoggetInnFeil() { // Denne er feil, ble ikke ferdig med den

        when(repository.sjekkLoggInn(anyString(), anyString())).thenReturn("Feil i personnummer eller passord");

        Map<String, Object> attributes = new HashMap<String, Object>();

        doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                String key = (String) invocation.getArguments()[0];
                Object value = invocation.getArguments()[1];
                attributes.put(key, value);
                return null;
            }
        }).when(session).setAttribute(anyString(), any());

        session.setAttribute("Innlogget", "12345678901");

        String resultat = sikkerhetsController.sjekkLoggInn("12345678901", "HeiHeiHei");

        assertEquals("Feil i personnummer eller passord", resultat);
    }
}
