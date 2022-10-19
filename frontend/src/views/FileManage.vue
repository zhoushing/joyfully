<template>
  <div style="width: 100%; padding: 10px">
    <el-backtop />

    <!--功能区域-->
    <div style="margin: 10px 0;">
      <el-upload
          :headers="headerObj"
          :action="backend_url + fileUploadTarget"
          :show-file-list=false
          :on-success="uploadSuccess"
          :limit="1"
          style="display: inline-block; margin: 0 10px"
      >
        <el-button type="primary">上传</el-button>
      </el-upload>
    </div>
    <!--搜索区域-->
    <div style="margin: 10px 0;">
      <el-input v-model="search" placeholder="请输入要搜索的文件名" style="width: 20%" clearable></el-input>
      <el-button type="primary" style="margin-left: 5px" @click="load">查询</el-button>
    </div>

    <!--加上border就会表单溢出-->
    <el-table
        :data="tableData"
        label-width="auto"
        style="width: 98%"
        height="68vh"
        stripe>

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
<!--      <el-table-column
          prop="uuid"
          label="文件标识">
      </el-table-column>-->
      <el-table-column
          prop="userId"
          label="所属用户ID">
      </el-table-column>
      <el-table-column
          prop="praiseCount"
          label="点赞数">
      </el-table-column>
      <el-table-column
          prop="belittleCount"
          label="点踩数">
      </el-table-column>

      <el-table-column
          label="预览"
      >
        <template class="demo-image__preview" #default="scope">
          <div v-if="scope.row.type === 'gif' || scope.row.type === 'jpg' || scope.row.type === 'png'">
            <el-image
                style="width: 100px; height: 100px"
                :src="tencent_cos + scope.row.uuid + '_' + scope.row.name + '.' + scope.row.type"
                :preview-src-list="[tencent_cos + scope.row.uuid + '_' + scope.row.name + '.' + scope.row.type]">
            </el-image>
          </div>
          <div v-else>暂不支持预览</div>
        </template>
      </el-table-column>
      <el-table-column
          fixed="right"
          label="操作">
        <template #default="scope">
          <el-button @click="handleDownload(scope.row.uuid)" type="primary" size="mini">下载</el-button>
          <el-popconfirm title="确定删除吗？" @confirm="handleDelete(scope.row.uuid, scope.$index)">
            <template #reference>
              <el-button type="danger" size="mini">删除</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <div style="margin: 10px 0">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-sizes="[5, 10, 20]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>
  </div>
</template>

<script>
import request from "../utils/request";
import { ElMessage, ElMessageBox } from 'element-plus'
import { ref } from 'vue'

export default {
  name: "FileManage",
  data() {
    return {
      form: {
        uuid: '',
        name: '',
        type: ''
      },
      tableData: [],
      fileTarget: '/file/',
      fileUploadTarget: '/file/upload',
      fileDownLoadTarget: '/file/download/',
      search: '',
      currentPage: 1,
      pageSize: 10,
      total: 0,
      backend_url: window.server.backend_url,
      tencent_cos: window.server.tencent_cos + '/',
      headerObj: {
        token: JSON.parse(sessionStorage.getItem('user')).token
      },
    }
  },
  created() {
    this.load();
  },
  methods: {
    load() {
      request.get(this.fileTarget + "getFilesInfo", {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search: this.search
        }
      }).then(res => {
        if (res.code === '00000') {
          this.tableData = res.data.records;
          this.total = res.data.total;
        }
        else {
          this.$message({
            type: "error",
            message: res.msg
          });
        }
      });
    },
    handleDownload(id) {
      let target = this.backend_url + this.fileDownLoadTarget + id;

      window.location.href = target
    },
    async handleDelete(uuid, index) {
      let target = this.fileTarget + uuid;

      await request.delete(target).then(res => {
        if (res.code === '00000') {
          this.tableData.splice(index, 1)
          this.$message({
            type: "success",
            message: "删除文件成功"
          });
        } else {
          this.$message.error(res.msg);
        }
      });
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
    uploadSuccess(res) {
      if (res.code === "00000") {
        this.$message.success("上传成功，文件路径为" + res.data);
        this.load();
      } else {
        this.$message.error("上传失败," + res.msg);
      }
    },

  }
}
</script>

<style scoped>

</style>