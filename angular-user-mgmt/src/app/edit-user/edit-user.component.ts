import { Component, OnInit } from '@angular/core';
import { User } from '../modal/user.modal';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ApiService } from '../core/api.service';
import { first } from 'rxjs/operators';

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.less']
})
export class EditUserComponent implements OnInit {

  user: User;
  editForm: FormGroup;
  constructor(private formBuilder: FormBuilder, private router: Router, private apiService: ApiService) { }

  ngOnInit() {
    const userId = window.localStorage.getItem('editUserId');
    if (!userId) {
      alert('Invalid action.');
      this.router.navigate(['list']);
      return;
    }

    this.editForm = this.formBuilder.group({
      id: [''],
      username: ['', Validators.required],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      age: ['', Validators.required],
      salary: ['', Validators.required]
    });

    this.apiService.getUserById(+userId).subscribe(data => {
      this.editForm.setValue(data.result);
    });
  }

  onSubmit() {
    this.apiService.updateUser(this.editForm.value).pipe(first()).subscribe(data => {
      if (data.status === 200) {
        alert('User updated successfully.');
        this.router.navigate(['list']);
      } else {
        alert(data.message);
      }
    },
      error => {
        alert(error);
      });
  }
}
