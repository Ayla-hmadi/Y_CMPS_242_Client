package com.example.yclient.Model;

import com.example.yclient.Model.enums.ReactionType;

public class AddReactionCommand {
    private int postId;
    private ReactionType reaction;

    public AddReactionCommand(int postId, ReactionType reactionType) {
        this.postId = postId;
        this.reaction = reactionType;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public ReactionType getReaction() {
        return reaction;
    }

    public void setReaction(ReactionType reactionType) {
        this.reaction = reactionType;
    }
}
