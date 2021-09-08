<template>
  <div style="text-align: center">
    <el-container style="margin-top: 10vh ;margin-left: 25%; max-width: 500px">
      <el-header></el-header>
      <el-main>
        <el-upload
            class="upload-demo"
            ref="upload"
            drag
            :action="fileUpload"
            multiple
            style="margin-top: 30px;"
            :accept="acceptFile()"
            show-file-list>
          <i class="el-icon-upload"></i>
          <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
          <template #tip>
            <div class="el-upload__tip">
              仅支持常见文件上传
            </div>
          </template>
        </el-upload>
      </el-main>
    </el-container>
  </div>
</template>

<script>

export default {
  name: "File",
  data() {
    return {
      fileList: [],
      /*  todo window.server.fileUploadUrl window.server.port */
      fileUpload: "http://" + "localhost" + ":" + "9090" + "/files/upload"
    }
  },
  methods: {
    acceptFile(e) {
      const allowHook = {
        video: '.mp4',
        audio: '.mp3,',
        file: '.doc, .docx, .pdf',
        excel: '.xlsx, .xls',
        img: '.jpg, .png, .gif'
      }
      if (e) {
        return allowHook[e];
      }
      let str = '';
      for (const k in allowHook) {
        str += allowHook[k] + " ,";
      }
      return str;
    },
  }
}
</script>

<style scoped>

</style>