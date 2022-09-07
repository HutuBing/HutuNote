/*
 * @Description: files
 * @Autor: liaojb
 * @Date: 2022-09-07 15:40:53
 * @LastEditors: liaojb
 * @LastEditTime: 2022-09-07 15:40:53
 */
import qs from 'qs'
import $http from '@/utils/http'
import { base_url } from '@/utils/config.js'

/**
 * @description: 上传文件
 * @Date: 2022-09-07 15:40:53
 * @author: liaojb
 */
export const upload = (params) => $http.post(`${base_url}/files/upload`, params)