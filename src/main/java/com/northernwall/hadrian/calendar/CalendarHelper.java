/*
 * Copyright 2014 Richard Thurston.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.northernwall.hadrian.calendar;

import com.northernwall.hadrian.domain.CalendarEntry;
import com.northernwall.hadrian.domain.Team;
import java.util.Date;
import java.util.List;

public abstract class CalendarHelper {
    
    public static final long ONE_MINUTE = 60*1000;
    public static final long ONE_HOUR = 60*ONE_MINUTE;
    public static final long ONE_DAY = 24*ONE_HOUR;
    public static final long MINUS_ONE_MINUTE = -1 * ONE_MINUTE;
    public static final long MINUS_ONE_HOUR = -1 * ONE_HOUR;
    public static final long MINUS_ONE_DAY = -1 * ONE_DAY;

    public abstract List<CalendarEntry> getCalendarEntries(Team team);
    
    public static String buildStartsEndsText(Date date) {
        return buildStartsEndsText(date.getTime());
    }
    
    public static String buildStartsEndsText(long date) {
        long dif = date - System.currentTimeMillis();
        
        if (dif < MINUS_ONE_DAY) {
            dif = dif / MINUS_ONE_DAY;
            if (dif == -1) {
                return "1 day ago";
            }
            return dif + " days ago";
        } else if (dif < MINUS_ONE_HOUR) {
            dif = dif / MINUS_ONE_HOUR;
            if (dif == -1) {
                return "1 hour ago";
            }
            return dif + " hours ago";
        } else if (dif <= 0) {
            dif = dif / MINUS_ONE_MINUTE;
            if (dif == -1) {
                return "1 minute ago";
            }
            return dif + " minutes ago";
        } else if (dif > ONE_DAY) {
            dif = dif / ONE_DAY;
            if (dif == 1) {
                return "in 1 day";
            }
            return "in " +dif + " days";
        } else if (dif > ONE_HOUR) {
            dif = dif / ONE_HOUR;
            if (dif == 1) {
                return "in 1 hour";
            }
            return "in " + dif + " hours";
        } else {
            dif = dif / ONE_MINUTE;
            if (dif == 1) {
                return "in 1 minute";
            }
            return "in " + dif + " minutes";
        }
    }
    
}
