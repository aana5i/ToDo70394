package local.hal.st42.android.todo75039;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;


public class DataAccess {

    static Cursor findAll(SQLiteDatabase db, int flag){
        String sql = "SELECT _id, name, deadline, done, note FROM tasks";
        if(flag != 2){
            sql += " WHERE done = "+flag+" ORDER BY deadline";
        }
        return db.rawQuery(sql, null);
    }

    static Task findByPK(SQLiteDatabase db, long id){
        String sql = "SELECT _id, name, deadline, done, note FROM tasks WHERE _id = " + id;
        Cursor cursor = db.rawQuery(sql, null);
        Task result = null;
        if(cursor.moveToFirst()){
            int idxName = cursor.getColumnIndex("name");
            int idxDeadline = cursor.getColumnIndex("deadline");
            int idxDone = cursor.getColumnIndex("done");
            int idxNote = cursor.getColumnIndex("note");
            String name = cursor.getString(idxName);
            String deadline = cursor.getString(idxDeadline);
            long done = cursor.getLong(idxDone);
            String note = cursor.getString(idxNote);

            result = new Task();
            result.setId(id);
            result.setName(name);
            result.setDeadline(deadline);
            result.setDone(done);
            result.setNote(note);
        }
        return result;
    }

    static void insert(SQLiteDatabase db, String name, String deadline, String note) {
        String sql = "INSERT INTO tasks(name, deadline, note) VALUES(?, ?, ?)";
        SQLiteStatement stmt = db.compileStatement(sql);
        stmt.bindString(1, name);
        stmt.bindString(2,deadline);
        stmt.bindString(3,note);
        long id = stmt.executeInsert();
    }

    static void update(SQLiteDatabase db, long id, String name, String deadline, long done, String note) {
        String sql = "UPDATE tasks SET name = ?, deadline = ?, done = ?, note = ? WHERE _id = ?";
        SQLiteStatement stmt = db.compileStatement(sql);
        stmt.bindString(1, name);
        stmt.bindString(2, deadline);
        stmt.bindLong(3, done);
        stmt.bindString(4, note);
        stmt.bindLong(5, id);
        stmt.executeUpdateDelete();
    }

    static void delete(SQLiteDatabase db, long id){
        String sql = "DELETE FROM tasks WHERE _id = ?";
        SQLiteStatement stmt = db.compileStatement(sql);
        stmt.bindLong(1, id);
        int result = stmt.executeUpdateDelete();
    }
}
