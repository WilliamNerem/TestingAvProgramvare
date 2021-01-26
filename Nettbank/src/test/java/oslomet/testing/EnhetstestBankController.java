package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.BankController;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Models.Transaksjon;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestBankController {

    @InjectMocks
    // denne skal testes
    private BankController bankController;

    @Mock
    // denne skal Mock'es
    private BankRepository repository;

    @Mock
    // denne skal Mock'es
    private Sikkerhet sjekk;

    @Test
    public void hentKundeInfo_loggetInn() {

        // arrange
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentKundeInfo(anyString())).thenReturn(enKunde);

        // act
        Kunde resultat = bankController.hentKundeInfo();

        // assert
        assertEquals(enKunde, resultat);
    }

    @Test
    public void hentKundeInfo_IkkeloggetInn() {

        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        //act
        Kunde resultat = bankController.hentKundeInfo();

        // assert
        assertNull(resultat);
    }

    @Test
    public void hentKonti_LoggetInn() {
        // arrange
        List<Konto> konti = new ArrayList<>();
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);
        Konto konto2 = new Konto("105010123456", "12345678901",
                1000, "Lønnskonto", "NOK", null);
        konti.add(konto1);
        konti.add(konto2);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentKonti(anyString())).thenReturn(konti);

        // act
        List<Konto> resultat = bankController.hentKonti();

        // assert
        assertEquals(konti, resultat);
    }

    @Test
    public void hentKonti_IkkeLoggetInn() {
        // arrange

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Konto> resultat = bankController.hentKonti();

        // assert
        assertNull(resultat);
    }

    @Test
    public void hentTransaksjoner_LoggetInn() {
        // arrange
        Konto konto = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);

        String kontoNr = "123456789";
        String fraDato = "01-01-2021";
        String tilDato = "01-01-2021";

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentTransaksjoner(anyString(), anyString(), anyString())).thenReturn(konto);

        // act
        Konto resultat = bankController.hentTransaksjoner(kontoNr, fraDato, tilDato);

        // assert
        assertEquals(konto, resultat);
    }

    @Test
    public void hentTransaksjoner_IkkeLoggetInn() {
        // arrange
        String kontoNr = "123456789";
        String fraDato = "01-01-2021";
        String tilDato = "01-01-2021";

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        Konto resultat = bankController.hentTransaksjoner(kontoNr, fraDato, tilDato);

        // assert
        assertNull(resultat);
    }

    @Test
    public void hentSaldi_LoggetInn() {
        // arrange
        List<Konto> konti = new ArrayList<>();
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);
        Konto konto2 = new Konto("105010123456", "12345678901",
                1000, "Lønnskonto", "NOK", null);
        konti.add(konto1);
        konti.add(konto2);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentSaldi(anyString())).thenReturn(konti);

        // act
        List<Konto> resultat = bankController.hentSaldi();

        // assert
        assertEquals(konti, resultat);
    }

    @Test
    public void hentSaldi_IkkeLoggetInn() {
        // arrange

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Konto> resultat = bankController.hentSaldi();

        // assert
        assertNull(resultat);
    }

    @Test
    public void hentBetalinger_LoggetInn() {
        // arrange
        List<Transaksjon> transaksjon = new ArrayList<>();
        Transaksjon transaksjon1 = new Transaksjon(123, "123456789", 1.2, "01-01-2021", "Test", "Avventer", "987654321");
        Transaksjon transaksjon2 = new Transaksjon(123, "111111111", 2.1, "01-01-2021", "Test", "Avventer", "999999999");
        transaksjon.add(transaksjon1);
        transaksjon.add(transaksjon2);

        when(sjekk.loggetInn()).thenReturn("123456789");

        when(repository.hentBetalinger(anyString())).thenReturn(transaksjon);

        // act
        List<Transaksjon> resultat = bankController.hentBetalinger();

        // assert
        assertEquals(transaksjon, resultat);
    }

    @Test
    public void hentBetalinger_IkkeLoggetInn() {
        // arrange

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Transaksjon> resultat = bankController.hentBetalinger();

        // assert
        assertNull(resultat);
    }

    @Test
    public void registrerBetaling_LoggetInn_OK() {
        //arrange
        Transaksjon transaksjon = new Transaksjon(123, "123456789", 1.2, "01-01-2021", "Test", "Avventer", "987654321");

        when(sjekk.loggetInn()).thenReturn("123456789");

        when(repository.registrerBetaling(any(Transaksjon.class))).thenReturn("OK");

        //act
        String resultat = bankController.registrerBetaling(transaksjon);

        //assert
        assertEquals("OK", resultat);

    }

    @Test
    public void registrerBetaling_LoggetInn_Feil() {
        //arrange
        Transaksjon transaksjon = new Transaksjon(123, "123456789", 1.2, "01-01-2021", "Test", "Avventer", "987654321");

        when(sjekk.loggetInn()).thenReturn("1234545");

        when(repository.registrerBetaling(any(Transaksjon.class))).thenReturn("Feil");

        //act
        String resultat = bankController.registrerBetaling(transaksjon);

        //assert
        assertEquals("Feil", resultat);

    }

    @Test
    public void registrerBetaling_IkkeLoggetInn() {
        //arrange
        Transaksjon transaksjon = new Transaksjon(123, "123456789", 1.2, "01-01-2021", "Test", "Avventer", "987654321");

        when(sjekk.loggetInn()).thenReturn(null);

        //act
        String resultat = bankController.registrerBetaling(transaksjon);

        //assert
        assertNull(resultat);

    }

    @Test
    public void endre_LoggetInn_OK() {
        //arrange
        Kunde innKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("123456789");

        when(repository.endreKundeInfo(any(Kunde.class))).thenReturn("OK");

        //act
        String resultat = bankController.endre(innKunde);

        //assert
        assertEquals("OK", resultat);
    }

    @Test
    public void endre_LoggetInn_Feil() {
        //arrange
        Kunde innKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("123456789");

        when(repository.endreKundeInfo(any(Kunde.class))).thenReturn("Feil");

        //act
        String resultat = bankController.endre(innKunde);

        //assert
        assertEquals("Feil", resultat);
    }

    @Test
    public void endre_IkkeLoggetInn() {
        //arrange
        Kunde innKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn(null);

        //act
        String resultat = bankController.endre(innKunde);

        //assert
        assertNull(resultat);
    }

    @Test
    public void utforBetaling_LoggetInn_OK()  {
        //arrange
        List<Transaksjon> transaksjon = new ArrayList<>();
        Transaksjon transaksjon1 = new Transaksjon(123, "123456789", 1.2, "01-01-2021", "Test", "Avventer", "987654321");
        Transaksjon transaksjon2 = new Transaksjon(123, "111111111", 2.1, "01-01-2021", "Test", "Avventer", "999999999");
        transaksjon.add(transaksjon1);
        transaksjon.add(transaksjon2);

        int txID = 9999999;

        when(sjekk.loggetInn()).thenReturn("123456789");

        when(repository.utforBetaling(any(int.class))).thenReturn("OK");

        when(repository.hentBetalinger(anyString())).thenReturn(transaksjon);

        //act
        List<Transaksjon> resultat = bankController.utforBetaling(txID);

        //assert
        assertEquals(transaksjon, resultat);

    }

    @Test
    public void utforBetaling_LoggetInn_Feil()  {
        //arrange
        List<Transaksjon> transaksjon = new ArrayList<>();
        Transaksjon transaksjon1 = new Transaksjon(123, "123456789", 1.2, "01-01-2021", "Test", "Avventer", "987654321");
        Transaksjon transaksjon2 = new Transaksjon(123, "111111111", 2.1, "01-01-2021", "Test", "Avventer", "999999999");
        transaksjon.add(transaksjon1);
        transaksjon.add(transaksjon2);

        int txID = 9999999;

        when(sjekk.loggetInn()).thenReturn("123456789");

        when(repository.utforBetaling(any(int.class))).thenReturn("Feil");

        //act
        List<Transaksjon> resultat = bankController.utforBetaling(txID);

        //assert
        assertNull(resultat);


    }

    @Test
    public void utforBetaling_IkkeLoggetInn()  {
        //arrange
        int txID = 9999999;

        when(sjekk.loggetInn()).thenReturn(null);

        //act
        List<Transaksjon> resultat = bankController.utforBetaling(txID);

        //assert
        assertNull(resultat);

    }
}

