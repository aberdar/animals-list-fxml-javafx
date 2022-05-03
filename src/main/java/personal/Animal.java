package personal;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Animal {

    private StringProperty view;
    private StringProperty nickname;
    private IntegerProperty years;
    private IntegerProperty months;
    private StringProperty hostname;

    public Animal(String view, String nickname, int years, int months, String hostname) {
        setView(view);
        setNickname(nickname);
        setYears(years);
        setMonths(months);
        setHostname(hostname);
    }

    public StringProperty viewStringProperty() {
        if (view == null) {
            view = new SimpleStringProperty();
        }
        return view;
    }

    public StringProperty nicknameStringProperty() {
        if (nickname == null) {
            nickname = new SimpleStringProperty();
        }
        return nickname;
    }

    public IntegerProperty yearsIntegerProperty() {
        if (years == null) {
            years = new SimpleIntegerProperty();
        }
        return years;
    }

    public IntegerProperty monthsIntegerProperty() {
        if (months == null) {
            months = new SimpleIntegerProperty();
        }
        return months;
    }

    public StringProperty hostnameStringProperty() {
        if (hostname == null) {
            hostname = new SimpleStringProperty();
        }
        return hostname;
    }

    public final void setView(String view) {
        viewStringProperty().set(view);
    }

    public final void setNickname(String nickname) {
        nicknameStringProperty().set(nickname);
    }

    public final void setYears(int years) {
        yearsIntegerProperty().set(years);
    }

    public final void setMonths(int months) {
        monthsIntegerProperty().set(months);
    }

    public final void setHostname(String hostname) {
        hostnameStringProperty().set(hostname);
    }

    public final String getView() {
        return viewStringProperty().get();
    }

    public final String getNickname() {
        return nicknameStringProperty().get();
    }

    public final int getYears() {
        return yearsIntegerProperty().get();
    }

    public final int getMonths() {
        return monthsIntegerProperty().get();
    }

    public final String getHostname() {
        return hostnameStringProperty().get();
    }
}
