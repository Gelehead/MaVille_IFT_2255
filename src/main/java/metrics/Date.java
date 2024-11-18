package metrics;

    // TODO: implement some kind of restriction to have month and day <= 12 and 31
    public class Date{
        int year, month, day;
        public Date(int year, int month, int day){
            this.day = day;
            this.month = month;
            this.year = year;
        }

        public static Date format(String s){
            return new Date(1, 1, 1);
        }
    }
