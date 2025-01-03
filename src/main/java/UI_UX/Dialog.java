package UI_UX;
import Utils.*;
import Utils.Language.language;
import backend.Database;


/*
 * La classe est la classe principale qui interagit avec la base de données et la classe speaker
 * 
 */
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
        RECHERCHER_PROJET_RESIDENT,
        IMPEDIMENT_RESIDENT,
        NOTIFS_RESIDENT,
        PLANIF_RESIDENT,
        PLACEHOLDER_RESIDENT,
        REQUEST_RESIDENT,
        VOTE_RESIDENT,
        SIGNAL_PRB_RESIDENT,


        MAIN_INTERVENANT,
        NEW_PROJECT_INTERVENANT,
        UPDATE_INTERVENANT,
        REQUEST_INTERVENANT,

        MAIN_ADMIN,

        QUIT,

        PLACEHOLDER,
    }

    public static Language.language choice_language = language.FRENCH;

    /* ------------------------ Graphic part ------------------------ */

    /*
     * La méthode init() est la méthode principale qui initialise le programme
     */
    public static void init(){
        Speaker.welcome();

        STATE state = STATE.INITIAL;
        Database database = new Database(mockIntervenants, mockResidents, mockUsers);

        while (state != STATE.QUIT) {
            // after each menu interaction, the state changes and a new menu is handled
            state = Speaker.menu(state, database);
        }

        System.out.println(Language.byebye(choice_language));
    }
}
