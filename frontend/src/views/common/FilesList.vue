<template>
  <div style="width: 100%; padding: 10px">
    <el-backtop />
    <!--加上border就会表单溢出-->
    <el-table
        :data="tableData"
        style="width: 98%;"
        stripe>
      <el-table-column
          prop="id"
          label="文件标识">
      </el-table-column>
      <el-table-column
          prop="name"
          label="文件名"
          sortable>
      </el-table-column>
      <el-table-column
          prop="type"
          label="文件类型"
          :filters="[
              { text: 'mp4', value: 'mp4' },
              { text: 'mp3', value: 'mp3' },
              { text: 'doc', value: 'doc' },
              { text: 'docx', value: 'docx' },
              { text: 'xlx', value: 'xls' },
              { text: 'xlsx', value: 'xlsx' },
              { text: 'pdf', value: 'pdf' },
              { text: 'jpg', value: 'jpg' },
              { text: 'png', value: 'png' },
              { text: 'gif', value: 'gif' }]"
          :filter-method="filterTag"
          filter-placement="bottom-end">
        <template #default="scope">
          <el-tag
              :type="scope.row.type === 'jpg' || scope.row.type === 'png' ? 'primary' : 'success'"
              disable-transitions>{{ scope.row.type }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column
          label="预览"
      >
        <template class="demo-image__preview" #default="scope">
          <div v-if="scope.row.type === 'gif' || scope.row.type === 'jpg' || scope.row.type === 'png'">
            <el-image
                style="width: 100px; height: 100px"
                :src="'http://localhost:9090/files/download/' + scope.row.id"
                :preview-src-list="['http://localhost:9090/files/download/' + scope.row.id]">
            </el-image>
          </div>
          <div v-else>暂不支持预览</div>
        </template>
      </el-table-column>
      <el-table-column
          fixed="right"
          label="操作">
        <template #default="scope">
          <el-button @click="handleDownload(scope.row)" type="primary" size="mini">下载</el-button>
        </template>
      </el-table-column>
    </el-table>

  </div>
</template>

<script>
import request from "../../utils/request";

export default {
  name: "FileList",
  data() {
    return {
      form: {
        id: '',
        name: '',
        type: ''
      },
      tableData: [],
      /*  todo window.server.fileUploadUrl window.server.port */
      fileDownload: "http://localhost:9090/files/download/"
    }
  },
  created() {
    let files = request.get("/files/getFilesInfo").then(res => {
      this.tableData = res.data;
    });
  },
  methods: {
    handleDownload(row) {
      /* 外部链接，所以使用window.location.href */
      window.location.href = this.fileDownload + row.id;
    },
    /* 改变每页的总个数时触发 */
    handleSizeChange: function (pageSize) {
      this.pageSize = pageSize;
      this.load();
    },
    /* 改变当前页码触发 */
    handleCurrentChange: function (pageNum) {
      this.currentPage = pageNum;
      this.load();
    },
    filterTag(value, row) {
      return row.type === value;
    },
  }
}
</script>

<style scoped>

</style>