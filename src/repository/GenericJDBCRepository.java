package repository;

import util.DatabaseConnection;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public abstract class GenericJDBCRepository<T> {
    protected Connection connection;
    protected Class<T> modelClass;
    protected String tableName;

    public GenericJDBCRepository(Class<T> modelClass, String tableName) throws SQLException {
        this.connection = DatabaseConnection.getInstance().getConnection();
        this.modelClass = modelClass;
        this.tableName = tableName;
    }

    public Optional<T> findById(Long id) {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + tableName + " WHERE id = ?")) {
            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToModel(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<T> findAll(Map<String, Object> conditions) {
        StringBuilder sql = new StringBuilder("SELECT * FROM " + tableName);
        if (!conditions.isEmpty()) {
            sql.append(" WHERE ");
            sql.append(String.join(" AND ", conditions.keySet().stream().map(key -> key + " = ?").collect(Collectors.toList())));
        }

        try (PreparedStatement stmt = connection.prepareStatement(sql.toString())) {
            int index = 1;
            for (Object value : conditions.values()) {
                stmt.setObject(index++, value);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                List<T> objects = new ArrayList<>();
                while (rs.next()) {
                    mapResultSetToModel(rs).ifPresent(objects::add);
                }
                return objects;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<T> findBy(String column, Object value) {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + tableName + " WHERE " + column + " = ?")) {
            statement.setObject(1, value);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToModel(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    public T save(Map<String, Object> data) {
        StringBuilder sql = new StringBuilder("INSERT INTO " + tableName + " (");
        sql.append(String.join(", ", data.keySet()));
        sql.append(") VALUES (");
        sql.append(String.join(", ", data.keySet().stream().map(key -> "?").toArray(String[]::new)));
        sql.append(")");

        try (PreparedStatement statement = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS)) {
            int index = 1;
            for (Object value : data.values()) {
                statement.setObject(index++, value);
            }
            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("La création de l'entité a échoué, aucune ligne affectée.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    Long newId = generatedKeys.getLong(1);
                    return findById(newId).orElseThrow(() -> new SQLException("la création de l'entité a échoué, aucun ID obtenu."));
                }
                throw new SQLException("La création de l'entité a échoué, aucun ID obtenu.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Map<String, Object> data, Long id) {
        StringBuilder sql = new StringBuilder("UPDATE " + tableName + " SET ");
        sql.append(String.join(", ", data.keySet().stream().map(key -> key + " = ?").toArray(String[]::new)));
        sql.append(" WHERE id = ?");

        try (PreparedStatement statement = connection.prepareStatement(sql.toString())) {
            int index = 1;
            for (Object value : data.values()) {
                statement.setObject(index++, value);
            }
            statement.setLong(index, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteById(Long id) {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM " + tableName + " WHERE id = ?")) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int count() {
        try (Statement statement = connection.createStatement()) {
            try (ResultSet rs = statement.executeQuery("SELECT COUNT(*) FROM " + tableName)) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    protected List<T> executeCustomQuery(String sql, Object... params) throws SQLException {
        List<T> results = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    mapResultSetToModel(rs).ifPresent(results::add);
                }
            }
        }
        return results;
    }

    protected abstract Optional<T> mapResultSetToModel(ResultSet resultSet) throws SQLException;

    protected abstract Map<String, Object> mapModelData(T model);
}