package server.chatbot.questions;

public class Query {

    private final String sql;

    public Query(String sql) {
        this.sql = sql;
    }

    public String getSql() {
        return sql;
    }

}
