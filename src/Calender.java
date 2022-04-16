import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.component.CalendarComponent;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

public class Calender {

    public static CalenderFrame start(MainMenuFrame mainMenuFrame) {
        CalenderFrame calenderFrame = new CalenderFrame(mainMenuFrame);

        return calenderFrame;
        /*
        try {
            iterateCalender(openCalender());
        }
        catch (ParserException | IOException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        } */
    }

    private static Calendar openCalender() throws ParserException, IOException {
        FileInputStream fin;
        try {
            fin = new FileInputStream("Timetable.ics");
        }
        catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        }
        CalendarBuilder builder = new CalendarBuilder();
        return builder.build(fin);
    }

    private static void iterateCalender(Calendar calendar) {
        for (CalendarComponent calendarComponent : calendar.getComponents()) {
            System.out.println("Component [" + ((Component) calendarComponent).getName() + "]");

            for (Property property : ((Component) calendarComponent).getProperties()) {
                System.out.println("Property [" + property.getName() + ", " + property.getValue() + "]");
            }
        }
    }
}
