		<!-- ----------------------------- General Info Module ----------------------------------->
		<fieldset id="general-info">
			<legend>General Info</legend>
			<div class="row">
				<div class="col-md-4">
					<div class="form-group">
						<label >Title</label> <input type="text" class="form-control" name="title"
							placeholder="Title"  value="${pd.title }"   readonly>
					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
						<label>Request ID</label> <input type="text" class="form-control" name="requestID"
							placeholder="Request ID"  value="${pd.requestID }"  readonly>
					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
						<label>Requestor Name</label> <input type="text" name="requestName"
							class="form-control" placeholder="Requestor Name" value="${pd.requestName }"  readonly>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-4">
					<div class="form-group">
						<label  class="required">Customer Code</label>
						 <input type="text" name="customerCode" class="form-control" placeholder="Customer Code" id="customerCode" value="${pd.customerCode }" readonly/>
							
					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
						<label  class="required">Device</label>
						  <input type="text" class="form-control" name="device" placeholder="Device" id="device" value="${pd.device }" readonly />
					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
						<label  class="required">Reason for Change</label>
							<input type="text" class="form-control" name="reason"   placeholder="Reason for Change" id="reason" value="${pd.reason }" readonly/>
                           
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-4">
					<div class="form-group">
						<label>Attached Test Result</label> 
						<input type="file"   name="attachedTestResultFile" id="uploadifive1" keepDefaultStyle = "true"/>
                        <div id="fileQueue1" class="fileQueue"></div>
                        <input type="hidden" name="attachedTestResult" id="attached-test-result" value="${pd.attachedTestResult} "/>	
                        <input type="hidden" value="no" id="hasTp1" />
					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
						<label>Attach Customer Approval</label> 
					    <input type="file"  name="attachCustomerApprovalFile" id="uploadifive2" keepDefaultStyle = "true"/>
					    <div id="fileQueue2" class="fileQueue"></div>
                        <input type="hidden" name="attachCustomerApproval" id="attach-customer-approval" value="${pd.attachCustomerApproval} "/>
                         <input type="hidden" value="no" id="hasTp2" />
					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
						<label>CPF/SPEC/WI# or Test Program Matrix</label> 
						<input type="text"  class="form-control" placeholder="Document Update" name="documentUpdate" value="${pd.documentUpdate }" readonly/>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-4">
					<div class="form-group">
						<label>Foundry (CP)</label>
						 <input type="text" class="form-control" name="foundry" value="${pd.foundry }" readonly/>
                            
					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
						<label>Other info</label> 
							<input type="text" class="form-control" name="other" value="${pd.other }" readonly/>
					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
						<label>Customer Release Date</label> 
						<input type="date" name="releaseDate" class="form-control" value="${pd.releaseDate }" readonly/>
					</div>
				</div>
			</div>

		</fieldset>

		<hr class="hr-module">
