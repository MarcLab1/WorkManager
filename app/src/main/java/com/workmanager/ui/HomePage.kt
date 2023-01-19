package com.workmanager.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomePage(vm: HomeViewModel = viewModel()) {

    Column() {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(onClick = { vm.addOneThing() }) {
                Text("add 1 thing")
            }
            Button(onClick = { vm.addThingPeriodically() }) {
                Text("add 1 thing periodicaly")
            }
        }
        Spacer(modifier = Modifier.padding(10.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(onClick = { vm.deleteAllThingsAndItems() }) {
                Text("delete all")
            }
            Button(onClick = { vm.chainRequests() }) {
                Text("chaining regular")
            }
        }
        Spacer(modifier = Modifier.padding(10.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(onClick = { vm.chainRequestsSlowly() }) {
                Text("chaining slowly")
            }
            Button(onClick = { vm.chainRequestsOnlyOneSlowly() }) {
                Text("chaining one slowly")
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
        )
        {
            stickyHeader {
                Text(
                    "Things",
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Color.LightGray
                        )
                )
            }
            itemsIndexed(vm.things.value)
            { index, thing ->
                Text("$index ${thing.toString()}")
            }
            item { Spacer(modifier = Modifier.padding(10.dp)) }
            stickyHeader {
                Text(
                    "Items",
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Color.LightGray
                        )
                )
            }
            itemsIndexed(vm.items.value)
            { index, item ->
                Text("$index ${item.toString()}")
            }
        }
    }
}