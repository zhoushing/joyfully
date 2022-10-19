<template>
  <div style="width: 100%; padding: 10px">
    <!--搜索区域-->
    <div style="margin: 10px 0;">
      <el-input v-model="search" placeholder="请输入要搜索的用户昵称" style="width: 20%" clearable></el-input>
      <el-button type="primary" style="margin-left: 5px" @click="load">查询</el-button>
    </div>

    <el-scrollbar max-height="72vh" style="height: 72vh">

      <!--加上border就会表单溢出-->
      <el-table
          :data="tableData"
          style="width: 98%"
          stripe
          v-loading="isLoad">

        <el-table-column
            prop="targetElem"
            label="评价目标"
            :filters="[
                { text: '问题', value: 'question' },
                { text: '答案', value: 'answer' },
                { text: '文件', value: 'file' }]"
            :filter-method="filterTagFromElem"
            filter-placement="bottom-end">
          <template #default="scope">
            <el-label
                disable-transitions>
              {{ scope.row.targetElem === 'answer' ? '答案' : (scope.row.type === 'file' ? '文件' : '问题')}}
            </el-label>
          </template>
        </el-table-column>
<!--        <el-table-column
            prop="targetId"
            label="评价目标id"
            sortable>
        </el-table-column>-->

<!--      父子传值未解决  -->
<!--        <el-table-column
            label="目标预览"
            width="560px"
            type="expand" >
          <template #default="scope">
            <el-main :targetObj="loadTarget(scope.row)" >
              <el-label v-if="scope.row.targetElem === 'answer'" prop="targetObj">
                答案内容：{{ issue }}
              </el-label>
              <el-label v-if="scope.row.targetElem === 'question'" prop="targetObj">
                问题内容：{{ content }}
              </el-label>
              <el-label v-if="scope.row.targetElem === 'file'" prop="targetObj">
                <el-button @click="loadFilePreview(this)" >点击预览</el-button>
              </el-label>
            </el-main>
          </template>
        </el-table-column>-->

        <el-table-column
            prop="type"
            label="评价类型"
            :filters="[
                { text: '差评', value: '差评' },
                { text: '举报', value: '举报' }]"
            :filter-method="filterTagFromType"
            filter-placement="bottom-end">
          <template #default="scope">
            <el-tag
                :type="scope.row.type === '举报' ? 'danger' : (scope.row.type === '好评' ? 'success' : '')"
                disable-transitions>{{scope.row.type}}</el-tag>
          </template>
        </el-table-column>
        <el-table-column
            prop="reason"
            label="举报原因"
            sortable>
        </el-table-column>
        <el-table-column
            prop="nickname"
            label="评价人昵称"
            width="120px"
            sortable>
        </el-table-column>
        <el-table-column
            fixed="right"
            width="160px"
            label="操作">
          <template #default="scope">
            <el-button @click="handleCheck(scope.row)" type="primary" size="mini">审查目标</el-button>
          </template>
        </el-table-column>
      </el-table>

    </el-scrollbar>

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

    <el-dialog center title="审查" v-model="checkVis" width="40%" >
      <template #default="scope">
        <el-form style="text-align: left">
          <el-form-item v-if="!isFile">
            审查目标内容：<br />
            <el-scrollbar max-height="200px">
              {{ checkObjShow }}
            </el-scrollbar>
          </el-form-item>
<!-- // todo         -->
          <el-form-item v-if="isFile">
            <el-button @click="loadFilePreview(checkObjShow)" >审查目标预览</el-button>
          </el-form-item>
          <el-form-item>
            举报理由：{{ checkObj.reason }}
            <br />
          </el-form-item>
          <el-form-item>
            <el-button type="success" size="mini" @click="reportPass()">审查完成，未违规</el-button>
            <el-button type="danger" size="mini" @click="reportDelete()">提交删除请求</el-button>
          </el-form-item>
        </el-form>
      </template>
    </el-dialog>

  </div>
</template>

<script>

import request from "../utils/request";
import {Base64} from 'js-base64'

export default {
  name: 'Audit',
  components: {},
  data() {
    return {
      form: {
        id: '',
        target: '',
        targetElem: '',
        targetId: '',
        type: '',
        reason: '',
        nickname: ''
      },
      dialogVisible: false,
      search: '',
      currentPage: 1,
      pageSize: 10,
      total: 10,
      tableData: [],
      isLoad: false,
      answerVis: false,
      answerList: [],
      checkVis: false,
      checkObj: {},
      checkObjShow: '',
      isFile: false,
      filename: '',
      kkFile: window.server.kk_file,
      tencent_cos: window.server.tencent_cos + '/',
    }
  },
  /* 初始加载方法 */
  created() {
    this.isLoad = true;
    this.load();
    this.isLoad = false;
  },
  methods: {
    load() {
      request.get("/evaluation/all", {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search: this.search
        }
      }).then(res => {
        if (res.code === '00000') {
          this.tableData = []
          for (let index in res.data.list) {
            let listKey = res.data.list[index]
            let item = {}
            item.id = listKey.id
            item.target = listKey.target
            item.targetElem = (listKey.target || "").split(",")[0]
            item.targetId = (listKey.target || "").split(",")[1]
            item.type = listKey.type
            item.reason = listKey.reason
            item.nickname = listKey.nickname
            item.userId = listKey.userId
            this.tableData.push(item)
          }
          this.total = res.data.total;
        }
        else {
          this.$message.error(res.msg);
        }
      });
    },
    handleCheck(row) {
      this.checkObj = row
      this.isFile = (row.targetElem === 'file')
      request.get("/evaluation/one", {
        params: {
          target: row.target
        }}).then(res => {
        if (res.code === '00000') {
          if (res.data.target === 'question') {
            this.checkObjShow = res.data.obj.issue
          }
          else if (res.data.target === 'answer') {
            this.checkObjShow = res.data.obj.content
          }
          else if (res.data.target === 'file') {
            this.checkObjShow = res.data.obj
          }

          this.checkVis = true
        }
        else {
          this.$message.error(res.msg);
        }
      })
    },
    reportPass() {
      console.log(this.checkObj);
      request.post("/evaluation/pass", this.checkObj).then(res => {
        if (res.code === '00000') {
          this.$message.success('审查成功')
        }
        else {
          this.$message.error(res.msg)
        }
      })

      this.checkVis = false;
      this.load();
    },
    reportDelete() {
      // todo
    },
    loadFilePreview(row) {
      let target = row.uuid + '_' + row.name + '.' + row.type
      let url = this.kkFile + '/onlinePreview?url=' + encodeURIComponent(Base64.encode(this.tencent_cos + target))
      window.open(url)
    },
    loadTarget(row) {
      console.log(row);
      return request.get("/evaluation/one", {
        params: {
          target: row.target
        }
      }).then(res => {
        if (res.code === '00000') {
          if (res.data.target === 'question') {
            return res.data.obj
          } else if (res.data.target === 'answer') {
            return res.data.obj
          } else if (res.data.target === 'file') {
            return res.data.obj
          }

          this.checkVis = true
        } else {
          this.$message.error(res.msg);
        }
      })
    },

    /* 改变每页的总个数时触发 */
    handleSizeChange(pageSize) {
      this.pageSize = pageSize;
      this.load();
    },
    /* 改变当前页码触发 */
    handleCurrentChange(pageNum) {
      this.currentPage = pageNum;
      this.load();
    },
    filterTagFromType(value, row) {
      return row.type === value;
    },
    filterTagFromElem(value, row) {
      return row.targetElem === value;
    },

  }
}
</script>
