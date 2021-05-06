package com.edu.austral.ingsis.dtos.home;

import com.edu.austral.ingsis.dtos.post.PostDTO;

public class HomeDTO {

  private PostDTO firstOfThread;
  private PostDTO madeByFollowed;

  public HomeDTO(PostDTO firstOfThread, PostDTO madeByFollowed) {
    this.firstOfThread = firstOfThread;
    this.madeByFollowed = madeByFollowed;
  }

  public PostDTO getFirstOfThread() {
    return firstOfThread;
  }

  public void setFirstOfThread(PostDTO firstOfThread) {
    this.firstOfThread = firstOfThread;
  }

  public PostDTO getMadeByFollowed() {
    return madeByFollowed;
  }

  public void setMadeByFollowed(PostDTO madeByFollowed) {
    this.madeByFollowed = madeByFollowed;
  }
}
