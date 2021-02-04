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
    public void test_sjekkLoggetInnFeil() { // Litt usikker på denne

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

        String resultat = sikkerhetsController.sjekkLoggInn("12345678901", "HeiHei");


        assertEquals("Feil i personnummer eller passord", resultat);
    }

    @Test
    public void test_sjekkLoggetInnAdminOK() {

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

        session.setAttribute("Innlogget", "Admin");

        String resultat = sikkerhetsController.loggInnAdmin("Admin","Admin");

        assertEquals("Logget inn", resultat);
    }

    @Test
    public void test_sjekkLoggetInnAdminFeil() {

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

        session.setAttribute("Innlogget", "Admin");

        String resultat1 = sikkerhetsController.loggInnAdmin("Admins","Admin");
        String resultat2 = sikkerhetsController.loggInnAdmin("Admin","Admins");

        assertEquals("Ikke logget inn", resultat1);
        assertEquals("Ikke logget inn", resultat2);
    }

    @Test
    public void test_LoggetInn() {
        Map<String,Object> attributes = new HashMap<String,Object>();

        doAnswer(new Answer<Object>(){
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                String key = (String) invocation.getArguments()[0];
                return attributes.get(key);
            }
        }).when(session).getAttribute(anyString());

        doAnswer(new Answer<Object>(){
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                String key = (String) invocation.getArguments()[0];
                Object value = invocation.getArguments()[1];
                attributes.put(key, value);
                return null;
            }
        }).when(session).setAttribute(anyString(), any());

        // arrange
        session.setAttribute("Innlogget","12345678901");
        // act
        String resultat = sikkerhetsController.loggetInn();
        // assert
        assertEquals("12345678901", resultat);
    }
}
