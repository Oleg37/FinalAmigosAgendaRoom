/*
 * Copyright (c) 2021. ArseneLupin0.
 *
 * Licensed under the GNU General Public License v3.0
 *
 * https://www.gnu.org/licenses/gpl-3.0.html
 *
 * Permissions of this strong copyleft license are conditioned on making available complete source
 * code of licensed works and modifications, which include larger works using a licensed work,
 * under the same license. Copyright and license notices must be preserved. Contributors provide
 * an express grant of patent rights.
 */

package es.miapp.ad.ej2amigosagenda.model.room.pojo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Entity(tableName = "call", foreignKeys = @ForeignKey(entity = Friend.class, parentColumns = "id",
        childColumns = "idFriend", onDelete = ForeignKey.CASCADE), indices = @Index(value = "idFriend"))
public class Call {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "idFriend")
    private long idFriend;

    @ColumnInfo(name = "callDate")
    private long callDate;
}