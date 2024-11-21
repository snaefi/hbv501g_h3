package is.hi.hbv501g.hbv501g_h3.Events;

import is.hi.hbv501g.hbv501g_h3.Persistence.Entities.KnittingPattern;
import is.hi.hbv501g.hbv501g_h3.Persistence.Entities.User;

public class PatternSharedEvent {

    private final KnittingPattern pattern;
    private final User user;

    public PatternSharedEvent(KnittingPattern pattern, User user) {
        this.pattern = pattern;
        this.user = user;
    }

    public KnittingPattern getPattern() {
        return pattern;
    }

    public User getUser() {
        return user;
    }
}

