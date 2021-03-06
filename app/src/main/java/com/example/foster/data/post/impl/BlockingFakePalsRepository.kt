/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.foster.data.post.impl

import android.content.Context
import com.example.foster.data.Result
import com.example.foster.data.post.PetPalsRepository
import com.example.foster.model.PetPal
import kotlinx.coroutines.flow.Flow

class BlockingFakePalsRepository(
    private val context: Context
) : PetPalsRepository {
    override suspend fun getPetPal(id: String): Result<PetPal> {
        TODO("Not yet implemented")
    }

    override suspend fun getPetPals(): Result<List<PetPal>> {
        return Result.Success(PET_PALS)
    }

    override fun observeAdoption(): Flow<Set<String>> {
        TODO("Not yet implemented")
    }

    override suspend fun toggleAdopt(id: String) {
        TODO("Not yet implemented")
    }
}
