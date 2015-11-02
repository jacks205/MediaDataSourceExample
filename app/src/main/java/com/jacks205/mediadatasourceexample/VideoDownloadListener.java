package com.jacks205.mediadatasourceexample;

public interface VideoDownloadListener {
    public void onVideoDownloaded();

    public void onVideoDownloadError(Exception e);
}
