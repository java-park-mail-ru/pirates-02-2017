package api.utils.validator;

/**
 * Created by coon on 08.03.17.
 */


public enum ValidatorStatus {
    ERROR("error"),
    WARNING("warning"),
    OK("ok")
    ;


    private final String text;


    /**
     * @param text
     */
    private ValidatorStatus(final String text) {
        this.text = text;
    }

    /**
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return text;
    }
}
