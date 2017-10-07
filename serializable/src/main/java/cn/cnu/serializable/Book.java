package cn.cnu.serializable;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/10/7.
 */

public class Book implements Parcelable {
    private int no;
    private String name;

    public Book(int no, String name) {
        this.no = no;
        this.name = name;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Book() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(no);
        dest.writeString(name);
    }
   public static  final Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel source) {
            Book book=new Book();
            book.no=source.readInt();
            book.name=source.readString();
            return book;
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
}
