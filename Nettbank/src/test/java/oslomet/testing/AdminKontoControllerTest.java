package oslomet.testing;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.AdminKontoController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Sikkerhet.Sikkerhet;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AdminKontoControllerTest {

    @InjectMocks
    // denne skal testes
    private AdminKontoController adminKontoController;

    @Mock
    // denne skal Mock'es
    private AdminRepository repository;

    @Mock
    // denne skal Mock'es
    private Sikkerhet sjekk;

    @Test
    public void testhentAlleKonti_loggetInn() {
        List<Konto> liste = new ArrayList<>();
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);
        Konto konto2 = new Konto("105010123456", "12345678901",
                1000, "Lønnskonto", "NOK", null);
        liste.add(konto1);
        liste.add(konto2);

        when(sjekk.loggetInn()).thenReturn("Admin");

        when(repository.hentAlleKonti()).thenReturn(liste);

        List<Konto> resultat = adminKontoController.hentAlleKonti();

        assertEquals(liste, resultat);
    }

    @Test
    public void testhentAlleKonti_ikkeloggetInn(){
        when(sjekk.loggetInn()).thenReturn(null);
        List<Konto> resultat = adminKontoController.hentAlleKonti();
        assertNull(resultat);
    }

    @Test
    public void testregistrerKonto_loggetInn(){
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);

        when(sjekk.loggetInn()).thenReturn("Admin");

        when(repository.registrerKonto(konto1)).thenReturn("OK");

        String resultat = adminKontoController.registrerKonto(konto1);

        assertEquals("OK", resultat);
    }

    @Test
    public void testregistrerKonto_ikkeloggetInn(){
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);

        when(sjekk.loggetInn()).thenReturn(null);

        String resultat = adminKontoController.registrerKonto(konto1);

        assertEquals("Ikke innlogget", resultat);
    }

    @Test
    public void testendreKonto_loggetInn(){
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);

        when(sjekk.loggetInn()).thenReturn("Admin");

        when(repository.endreKonto(konto1)).thenReturn("OK");

        String resultat = adminKontoController.endreKonto(konto1);

        assertEquals("OK", resultat);
    }

    @Test
    public void testendreKonto_ikkeloggetInn(){
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);

        when(sjekk.loggetInn()).thenReturn(null);

        String resultat = adminKontoController.endreKonto(konto1);

        assertEquals("Ikke innlogget", resultat);
    }

    @Test
    public void testslettKonto_loggetInn(){
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);

        when(sjekk.loggetInn()).thenReturn("Admin");

        when(repository.slettKonto(anyString())).thenReturn("OK");

        String resultat = adminKontoController.slettKonto(konto1.getKontonummer());

        assertEquals("OK", resultat);
    }

    @Test
    public void testslettKonto_ikkeloggetInn(){
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);

        when(sjekk.loggetInn()).thenReturn(null);

        String resultat = adminKontoController.slettKonto(konto1.getKontonummer());

        assertEquals("Ikke innlogget", resultat);
    }
}