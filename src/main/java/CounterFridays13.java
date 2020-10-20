import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.DateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
//Мне не очень нравится решение, но я не знаю как переделать while в stream
public class CounterFridays13 {

    public LinkedHashMap<Integer, Long> countFridays(String yearStart, String yearEnd){
        if(Integer.parseInt(yearStart)> Integer.parseInt(yearEnd)){
            String  temp = yearStart;
            yearStart = yearEnd;
            yearEnd = temp;
        }
        String start = "01/01/"+yearStart;
        String end = "12/09/" +yearEnd;
        DateTimeFormatter pattern = DateTimeFormat.forPattern("dd/MM/yyyy");
        DateTime startDate = pattern.parseDateTime(start);
        DateTime endDate = pattern.parseDateTime(end);
        List<Integer> fridaysYears = new ArrayList<>();

        while (startDate.isBefore(endDate)){
            if ( startDate.getDayOfWeek() == DateTimeConstants.FRIDAY && startDate.getDayOfMonth() == 13 ){
               fridaysYears.add(startDate.getYear());
            }
            startDate = startDate.plusDays(1);

        }


         return fridaysYears.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .sorted((e1,e2)->
                        Long.compare(e2.getValue(), e1.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));


    }

    public static void main(String[] args) {
        CounterFridays13 cf = new CounterFridays13();
        cf.countFridays("2020", "2013");
    }

}
