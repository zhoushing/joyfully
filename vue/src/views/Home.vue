<template>
  <div style="width: 100%; padding: 10px">
    <!--功能区域-->
    <div style="margin: 10px 0;">
      <el-button type="primary">新增</el-button>
      <el-button type="primary">导入</el-button>
      <el-button type="primary">导出</el-button>
    </div>
    <!--搜索区域-->
    <div style="margin: 10px 0;">
      <el-input v-model="search" placeholder="请输入要搜索的内容" style="width: 20%"></el-input>
      <el-button type="primary" style="margin-left: 5px" >查询</el-button>
    </div>
    <el-table
        :data="tableData"
        border
        style="width: 100%"
        stripe>
      <el-table-column
          prop="date"
          label="日期"
          sortable>
      </el-table-column>
      <el-table-column
          prop="name"
          label="姓名">
      </el-table-column>
      <el-table-column
          prop="address"
          label="地址">
      </el-table-column>
      <el-table-column
          fixed="right"
          label="操作">
        <template #default="scope">
          <el-button @click="handleEdit(scope.$index, scope.row)" type="primary" size="mini">编辑</el-button>
          <el-popconfirm title="确定删除吗？">
            <template #reference>
              <el-button @click="handleDelete(scope.$index, scope.row)" type="danger" size="mini">删除</el-button>
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
          :page-size="10"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>
  </div>
</template>

<script>

export default {
  name: 'Home',
  components: {

  },
  data() {
    return {
      search: '',
      currentPage: 1,
      total: 10,
      tableData: [{
        date: '2016-05-02',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄'
      }, {
        date: '2016-05-04',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1517 弄'
      }, {
        date: '2016-05-01',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1519 弄'
      }, {
        date: '2016-05-03',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1516 弄'
      }]
    }
  },
  methods: {
    handleEdit: ()=>{

    },
    handleDelete: ()=>{

    },
    handleSizeChange: ()=>{

    },
    handleCurrentChange: ()=>{

    },
  }
}
</script>

























































