package kz.kolesateam.confapp.di

import kz.kolesateam.confapp.events.data.datasource.BranchIdDataSource
import kz.kolesateam.confapp.events.data.datasource.BranchIdMemoryDataSource
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val BRANCH_ID_DATA_SOURCE = "branch_id_data_source"

val branchIdModule: Module = module {
    single(named(BRANCH_ID_DATA_SOURCE)) {
        BranchIdMemoryDataSource() as BranchIdDataSource
    }
}