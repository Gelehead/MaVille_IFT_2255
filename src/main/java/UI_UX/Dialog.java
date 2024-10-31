package UI_UX;
import Utils.*;
import Utils.Language.language;
import backend.Database;

//Dialog class is the main discussion class, interacts with the database and speaker class,
public class Dialog {

    private static int mockIntervenants = 3;
    private static int mockResidents = 3;
    private static int mockUsers = 3;

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

        QUIT,

        PLACEHOLDER,
    }

    public static Language.language choice_language = language.FRENCH;

    /* ------------------------ Graphic part ------------------------ */

    public static void init(){
        Speaker.welcome();

        STATE state = STATE.REGISTER;
        Database database = new Database(mockIntervenants, mockResidents, mockUsers);

        while (state != STATE.QUIT) {
            // after each menu interaction, the state changes and a new menu is handled
            state = Speaker.menu(state, database);
        }
    }
}
