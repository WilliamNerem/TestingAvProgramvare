package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.AdminKontoController;
import oslomet.testing.API.AdminKundeController;
import oslomet.testing.API.BankController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetsAdminKunde {

    @InjectMocks
    private AdminKundeController adminKundeController;

    @Mock
    private AdminRepository repository;

    @Mock
    private Sikkerhet sjekk;

    @Test
    public void testHentKunder_loggetInn() {

        List<Kunde> kunder = new ArrayList<>();
        Kunde kunde1 = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "1234",
                "Asker", "22224444", "HeiHei");

        Kunde kunde2 = new Kunde("02020210629",
                "Gunnar", "Gunnarsen", "Osloveien 19", "0183",
                "Oslo", "33338888", "ElskerOslo");

        kunder.add(kunde1);
        kunder.add(kunde2);

        when(sjekk.loggetInn()).thenReturn("Admin");

        when(repository.hentAlleKunder()).thenReturn(kunder);


        List<Kunde> resultat = adminKundeController.hentAlle();


        assertEquals(kunder, resultat);

    }

    @Test
    public void testHentKunder_IkkeloggetInn() {

        when(sjekk.loggetInn()).thenReturn(null);

        List<Kunde> resultat = adminKundeController.hentAlle();

        assertNull(resultat);

    }

    @Test
    public void testLagreKunde_loggetInn() {

        Kunde kunde1 = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "1234",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("Admin");

        when(repository.registrerKunde(any(Kunde.class))).thenReturn("OK");

        String resultat = adminKundeController.lagreKunde(kunde1);

        assertEquals("OK", resultat);
    }

    @Test
    public void testLagreKunde_IkkeloggetInn() {

        Kunde kunde1 = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "1234",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn(null);

        String resultat = adminKundeController.lagreKunde(kunde1);

        assertEquals("Ikke logget inn", resultat);

    }

    @Test
    public void testEndreKunde_loggetInn() {

        Kunde kunde1 = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "1234",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("Admin");

        when(repository.endreKundeInfo(any(Kunde.class))).thenReturn("OK");

        String resultat = adminKundeController.endre(kunde1);

        assertEquals("OK",resultat);

    }

    @Test
    public void testEndreKunde_IkkeloggetInn() {

        Kunde kunde1 = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "1234",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn(null);

        String resultat = adminKundeController.endre(kunde1);

        assertEquals("Ikke logget inn", resultat);

    }

    @Test
    public void testSlettKunde_loggetInn() {

        Kunde kunde1 = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "1234",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("Admin");

        when(repository.slettKunde(anyString())).thenReturn("OK");

        String resultat = adminKundeController.slett(kunde1.getPersonnummer());

        assertEquals("OK",resultat);

    }

    @Test
    public void testSlettKunde_IkkeloggetInn() {

        Kunde kunde1 = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "1234",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn(null);

        String resultat = adminKundeController.slett(kunde1.getPersonnummer());

        assertEquals("Ikke logget inn", resultat);

    }

}
