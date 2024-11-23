package Utils;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    @Test
    public void testParseTravauxLocal() {
        String filePath = "src/test/resources/TestTravaux.json";
        List<Parser.Record> records = Parser.getRecordsFromLocalFile(filePath);

        // Vérifier résultats
        assertNotNull(records, "erreur : record null");
        assertEquals(2, records.size(), "erreur : record != 2 ");

        // Tester tous les records
        for (Parser.Record record : records) {
            assertNotNull(record.official_id, "erreur : id null ");
            assertNotNull(record.boroughid, "erreur : borough id null");
            assertNotNull(record.duration_start_date, "erreur : date du debut null");
            assertNotNull(record.organizationname, "erreur : nom organisation null ");

            // Vérifier que les dates sont valides
            assertTrue(record.duration_start_date.matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}Z"),
                    "La date de début doit être au format ISO 8601");
        }

        // Vérifier un record spécifique
        Parser.Record record = records.get(0);
        assertEquals("661e83541c002b0019d3f0e1", record.official_id);
        assertEquals("Rivière-des-Prairies-Pointe-aux-Trembles", record.boroughid);
        assertEquals("Travaux", record.permitcategory);
        assertEquals("Permis émis", record.currentstatus);
        assertEquals("Les entreprises Roseneige", record.organizationname);
    }


    @Test
    public void testParseEntravesLocal() {
        String filePath = "src/test/resources/TestEntraves.json";
        List<Parser.Impediment> impediments = Parser.getImpedimentsFromLocalFile(filePath);

        // Vérifier résultats
        assertNotNull(impediments, "erreur : travaux nulle");
        assertEquals(2, impediments.size(), "erreur : entraves != 2 ");

        // Tester tous les records
        for (Parser.Impediment impediment : impediments) {
            assertNotNull(impediment.idRequest, "erreur : id de requete null ");
            assertNotNull(impediment.name, "erreur : nom de la rue null ");
            assertNotNull(impediment.streetimpacttype, "erreur : type d'impact null ");

            // Vérifier que les longueurs sont numériques
            assertTrue(impediment.length.matches("\\d+(\\.\\d+)?"), "La longueur doit être un nombre valide");

            // Vérifier que les champs boolean contiennent 't' ou 'f'
            assertTrue(impediment.isarterial.equals("t") || impediment.isarterial.equals("f"),
                    "isArterial doit être 't' ou 'f'");
        }

        // Vérifier une entrave spécifique
        Parser.Impediment impediment = impediments.get(0);
        assertEquals("65b40710675f6600194688fe", impediment.idRequest);
        assertEquals("avenue Earnscliffe", impediment.name);
        assertEquals("0-3 mètres", impediment.streetimpactwidth);
        assertEquals("Aucun impact / non applicable", impediment.streetimpacttype);
    }
}