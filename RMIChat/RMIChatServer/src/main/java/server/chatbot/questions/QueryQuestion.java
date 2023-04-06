package server.chatbot.questions;

import server.chatbot.ParamValue;
import server.chatbot.Phrase;
import server.db.MySQLConnect;
import server.utils.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Will query and return a result
 */
public class QueryQuestion extends Question {

    private final Query query;

    private String outputMessage;

    public QueryQuestion(Phrase phrase, Query query, String outputMessage, Param... params) {
        super(phrase, params);
        this.query = query;
        this.outputMessage = outputMessage;
    }

    @Override
    public String action(ParamValue... params) {

        try (Connection connection = MySQLConnect.getConnection()) {
            String query = this.getQuery().getSql();

            int numberOfParams = query.split("\\?").length - 1;

            PreparedStatement statement = connection.prepareStatement(query);

            for (int i = 0; i < numberOfParams; i++) {
                Param param = this.getParams()[i];
                final int parameterIndex = i + 1;
                if (param.getClazz().equals(String.class)) {
                    statement.setString(parameterIndex, params[i].getValue());
                } else if (param.getClazz().equals(Integer.class)) {
                    statement.setInt(parameterIndex, Integer.parseInt(params[i].getValue()));
                }
            }

            final ResultSet resultSet = statement.executeQuery();

            if (resultSet != null && resultSet.next()) {
                final String result = resultSet.getString(1);
                if (result != null)
                    return StringUtils.format(outputMessage, "%s", result);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return "Nada encontrado";
    }

    public Query getQuery() {
        return query;
    }

    public String getOutputMessage() {
        return outputMessage;
    }

    public void setOutputMessage(String outputMessage) {
        this.outputMessage = outputMessage;
    }

}
