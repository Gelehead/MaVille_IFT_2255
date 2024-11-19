package UI_UX;
import Utils.*;
import Utils.Language.language;
import backend.Database;

//Dialog class is the main discussion class, interacts with the database and speaker class,
public class Dialog {

    private static int mockIntervenants = 10;
    private static int mockResidents = 10;
    private static int mockUsers = 10;

    // put menu states here
    public enum STATE {
        INITIAL,

        LOGIN,
        REGISTER,

        MAIN_RESIDENT,
        TRAVAUX_RESIDENT,
        NOTIFS_RESIDENT,
        PLANIF_RESIDENT,
        PLACEHOLDER_RESIDENT,

        MAIN_INTERVENANT,

        MAIN_ADMIN,

        QUIT,

        PLACEHOLDER,
    }

    public static Language.language choice_language = language.FRENCH;

    /* ------------------------ Graphic part ------------------------ */

    public static void init(){
        Speaker.welcome();

        STATE state = STATE.INITIAL;
        Database database = new Database(mockIntervenants, mockResidents, mockUsers);

        while (state != STATE.QUIT) {
            // after each menu interaction, the state changes and a new menu is handled
            state = Speaker.menu(state, database);
        }
    }
}
