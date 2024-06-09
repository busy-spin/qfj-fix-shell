package io.github.busy_spin.qfj_fix_shell.qfj;

import io.github.busy_spin.qfj_fix_shell.qfj.utils.logs.LogElement;
import io.github.busy_spin.qfj_fix_shell.qfj.utils.logs.QueueLog;
import io.github.busy_spin.qfj_fix_shell.qfj.utils.logs.QueuingLogFactory;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.shell.table.ArrayTableModel;
import org.springframework.shell.table.BorderStyle;
import org.springframework.shell.table.TableBuilder;
import quickfix.SessionID;
import quickfix.SessionSettings;

import java.util.*;

public class ShellPrinter {


    public void printLogs(QueuingLogFactory logFactory) {
        Map<SessionID, QueueLog> sessionLogs = logFactory.getLogs();
        for (SessionID sessionID : sessionLogs.keySet()) {
            AnsiOutput.toString(AnsiColor.BRIGHT_BLUE,
                    "Session ID" + sessionID.toString(), AnsiColor.DEFAULT);
            System.out.println("Session ID " + sessionID);
            List<String[]> rows = new ArrayList<>();
            rows.add(new String[]{"Type", "Message"});

            QueueLog queueLog = sessionLogs.get(sessionID);
            for (Object obj : queueLog.getQueue().toArray()) {
                LogElement element = (LogElement) obj;
                rows.add(new String[]{element.getType(), element.getMessage()});
            }

            ArrayTableModel arrayTableModel = new ArrayTableModel(rows.toArray(new String[0][0]));
            TableBuilder tableBuilder = new TableBuilder(arrayTableModel)
                    .addFullBorder(BorderStyle.fancy_heavy);

            System.out.println(tableBuilder.build().render(1000));
        }
    }

    public void printSessions(SessionSettings settings) {
        ArrayList<String[]> sessions = new ArrayList<>();
        settings.sectionIterator().forEachRemaining(id -> sessions.add(new String[]{id.toString()}));
        if (sessions.isEmpty()) {
            System.out.println("No sessions found");
        } else {
            ArrayTableModel arrayTableModel = new ArrayTableModel(sessions.toArray(new String[0][0]));
            TableBuilder tableBuilder = new TableBuilder(arrayTableModel)
                    .addFullBorder(BorderStyle.fancy_heavy);

            System.out.println(tableBuilder.build().render(1000));
        }

    }

    public void printSessionDetails(SessionSettings settings, String sessionId) throws Exception {

        Properties properties = settings.getSessionProperties(new SessionID(sessionId));
        ArrayList<String[]> sessionDetails = new ArrayList<>();

        Properties defaultProperties = settings.getDefaultProperties();

        HashMap<Object, Object> mergeConfigs = new HashMap<>();
        mergeConfigs.putAll(defaultProperties);
        mergeConfigs.putAll(properties);

        for (Map.Entry<Object, Object> entry : mergeConfigs.entrySet()) {
            sessionDetails.add(new String[]{entry.getKey().toString(), entry.getValue().toString()});
        }

        ArrayTableModel arrayTableModel = new ArrayTableModel(sessionDetails.toArray(new String[0][0]));
        TableBuilder tableBuilder = new TableBuilder(arrayTableModel)
                .addFullBorder(BorderStyle.fancy_heavy);

        System.out.println(tableBuilder.build().render(1000));
    }

    public void printSequenceNumbers(int nextNumIn, int nextNumOut) {
        ArrayTableModel arrayTableModel = new ArrayTableModel(new String[][]{
                new String[]{"NextNumOut", String.valueOf(nextNumOut)},
                new String[]{"NextNumIn", String.valueOf(nextNumIn)}
        });
        TableBuilder tableBuilder = new TableBuilder(arrayTableModel)
                .addFullBorder(BorderStyle.fancy_heavy);

        System.out.println(tableBuilder.build().render(1000));
    }
}