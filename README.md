uploadphoto
相机是打开相机照相后在onActivityResult中进行判断是否是相机如果是就跳转到裁剪图片界面。裁剪结束后同样在onActivityResult处理裁剪结果并显示。
相册则是直接打开相册后直接裁剪，裁剪后经过onActivityResult来显示。
裁剪判断
case REQUEST_CROP:
                    if (isPhotoReallyCropped(cropParams.uri)) {
                        Log.d(TAG, "Photo cropped!");
                        onPhotoCropped(handler, cropParams);
                        break;
                    } else {
                        Context context = handler.getCropParams().context;
                        if (context != null) {
                            if (data != null && data.getData() != null) {
                                String path = CropFileUtils.getSmartFilePath(context, data.getData());
                                boolean result = CropFileUtils.copyFile(path, cropParams.uri.getPath());
                                if (!result) {
                                    handler.onFailed("Copy file to cached folder failed");
                                    break;
                                }
                            } else {
                                handler.onFailed("Returned data is null " + data);
                                break;
                            }
                        } else {
                            handler.onFailed("CropHandler's context MUST NOT be null!");
                        }
                    }
                    该处是执行便生成一个默认文件，如果相机来的会裁剪则文件长度肯定大于0，相册来的照片是个空文件所以长度为0，
                    可以通过这个来进行显示的相应操作。
